package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostTimelineService;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.PostTimelineRepository;
import com.fyself.post.service.post.datasource.domain.PostTimeline;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.fyself.post.service.post.contract.AnswerSurveyBinder.ANSWER_SURVEY_BINDER;
import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.service.post.contract.PostTimelineBinder.POST_TIMELINE_BINDER;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.just;

@Service("postTimelineService")
@Validated
public class PostTimelineServiceImpl implements PostTimelineService {
    private final PostTimelineRepository repository;
    final AnswerSurveyRepository answerSurveyRepository;

    public PostTimelineServiceImpl(PostTimelineRepository repository, AnswerSurveyRepository answerSurveyRepository) {
        this.repository = repository;
        this.answerSurveyRepository = answerSurveyRepository;
    }

    @Override
    public Mono<String> create(@NotNull @Valid PostTimelineTO to) {
        return repository
                .save(POST_TIMELINE_BINDER.bind(to.withCreatedAt().withUpdatedAt()))
                .map(PostTimeline::getUser);
    }

    @Override
    public Mono<PagedList<PostTO>> search(PostTimelineCriteriaTO criteria, FySelfContext context) {
        return repository.findPage(POST_BINDER.bindToTimelineCriteria(criteria.withUser(context.getAccount().get().getId())))
                .map(postTimelines -> POST_BINDER.bindPageTimeline(postTimelines, context.getAccount().get().getId()))
                .flatMap(postTOPagedList -> fromIterable(postTOPagedList.getElements())
                        .flatMap(postTO -> answerSurveyRepository.findByPostAndUser(postTO.getId(), context.getAccount().get().getId())
                                .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
                                .map(answerSurveyTO -> POST_BINDER.bindPostTOWithAnswer(postTO, answerSurveyTO))
                                .switchIfEmpty(just(postTO)), 1)
                        .collectList()
                        .map(postTOS -> POST_BINDER.bind(postTOPagedList, postTOS)));
    }
}
