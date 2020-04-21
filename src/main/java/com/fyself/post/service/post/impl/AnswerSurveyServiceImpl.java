package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.AnswerSurveyService;
import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.AnswerSurveyTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.criteria.AnswerSurveyCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.enums.TypeSearch;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.post.service.stream.StreamService;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.fyself.post.service.post.contract.AnswerSurveyBinder.ANSWER_SURVEY_BINDER;
import static com.fyself.post.service.stream.contract.KafkaMessageBinder.KAFKA_MESSAGE_BINDER;
import static com.fyself.post.tools.LoggerUtils.*;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

@Service("answerSurveyService")
@Validated
public class AnswerSurveyServiceImpl implements AnswerSurveyService {

    final AnswerSurveyRepository repository;
    final PostService postService;
    private final StreamService streamService;

    public AnswerSurveyServiceImpl(AnswerSurveyRepository repository, PostService postService, StreamService streamService) {
        this.repository = repository;
        this.postService = postService;
        this.streamService = streamService;
    }

    @Override
    public Mono<String> add(@Valid AnswerSurveyTO to, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> repository.save(ANSWER_SURVEY_BINDER.bind(to.withOwner(userId).withId(to.getPost()).withCreateAt().withUpdateAt())))
                .doOnSuccess(survey -> this.putInPipeline(survey).subscribe())
                .doOnSuccess(entity -> createEvent(entity, context))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }

    @Override
    public Mono<Void> update(AnswerSurveyTO to, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId ->
                        repository.getById(to.getId())
                                .flatMap(survey -> repository.save(ANSWER_SURVEY_BINDER.bind(
                                        to.withCreateAt(survey.getCreatedAt())
                                                .withUpdateAt()
                                                .withOwner(userId))
                                        )
                                                .doOnSuccess(entity -> updateEvent(survey, entity, context))
                                                .doOnSuccess(response -> this.putInPipeline(response).subscribe())
                                )
                )
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<AnswerSurveyTO> patch(String id, HashMap to, FySelfContext context) {
        return repository.getById(id)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .flatMap(survey ->
                        just(ANSWER_SURVEY_BINDER.patch(survey, to))
                );
    }

    @Override
    public Mono<Void> delete(String id, FySelfContext context) {
        return repository.getById(id).flatMap(survey ->
                repository.softDelete(survey).doOnSuccess(entity -> deleteEvent(survey, context))
        ).switchIfEmpty(error(EntityNotFoundException::new)).then();
    }

    @Override
    public Mono<AnswerSurveyTO> load(String id, String post, FySelfContext context) {
        return repository.getById(id)
                .filter(survey -> survey.getPost().getId().equals(post))
                .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
                .switchIfEmpty(error(EntityNotFoundException::new));
    }

    @Override
    public Mono<PagedList<AnswerSurveyTO>> loadAll(AnswerSurveyCriteriaTO criteria, FySelfContext context) {
        return repository.findPage(ANSWER_SURVEY_BINDER.bind(criteria)).map(ANSWER_SURVEY_BINDER::bind);
    }

    @Override
    public Mono<PagedList<AnswerSurveyTO>> loadAllByMe(AnswerSurveyCriteriaTO criteria, FySelfContext context) {
        return postService.searchMe(buildPostCriteria(context.getAccount().get().getId()), context)
                .map(PagedList::getElements)
                .map(this::getPostIds)
                .flatMap(ids -> repository.findPage(ANSWER_SURVEY_BINDER.bind(criteria.withPostIds(ids))))
                .map(ANSWER_SURVEY_BINDER::bind);
    }

    private Mono<Void> putInPipeline(AnswerSurvey answer) {
        return streamService.putInPipelineAnswerElastic(KAFKA_MESSAGE_BINDER.bindAnswer(answer));
    }

    private PostTimelineCriteriaTO buildPostCriteria(String user) {
        PostTimelineCriteriaTO criteria = new PostTimelineCriteriaTO().withUser(user);
        criteria.setType(TypeSearch.ME);
        return criteria;
    }

    private List<String> getPostIds(List<PostTO> posts) {
        List<String> ids = new ArrayList<>();
        new ArrayList<>(posts).forEach(post -> ids.add(post.getId()));
        return ids;
    }
}
