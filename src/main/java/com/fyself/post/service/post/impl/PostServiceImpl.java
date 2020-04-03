package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.PostShareTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.criteria.PostCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.post.service.post.datasource.PostTimelineRepository;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;

import static com.fyself.post.service.post.contract.AnswerSurveyBinder.ANSWER_SURVEY_BINDER;
import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.service.post.contract.PostTimelineBinder.POST_TIMELINE_BINDER;
import static com.fyself.post.tools.LoggerUtils.*;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

@Service("postService")
@Validated
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final PostTimelineRepository postTimelineRepository;
    final AnswerSurveyRepository answerSurveyRepository;

    public PostServiceImpl(PostRepository repository, PostTimelineRepository postTimelineRepository, AnswerSurveyRepository answerSurveyRepository) {
        this.repository = repository;
        this.postTimelineRepository = postTimelineRepository;
        this.answerSurveyRepository = answerSurveyRepository;
    }

    @Override
    public Mono<String> create(@NotNull @Valid PostTO to, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> repository.save(POST_BINDER.bind(to.withUserId(userId).withCreatedAt().withUpdatedAt()))
                        .flatMap(post -> postTimelineRepository.save(POST_TIMELINE_BINDER.bind(post))
                                .thenReturn(post)))
                .doOnSuccess(entity -> createEvent(entity, context))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }

    @Override
    public Mono<Void> update(@NotNull @Valid PostTO to, FySelfContext context) {
        return repository.findById(to.getId())
                .map(post -> POST_BINDER.set(post, to.withUpdatedAt()))
                .flatMap(post -> repository.save(post)
                        .doOnSuccess(entity -> updateEvent(post, entity, context)))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<PostTO> load(@NotNull String id, FySelfContext context) {
        return repository.findById(id)
                .flatMap(post -> answerSurveyRepository.findByPostAndUser(post.getId(), context.getAccount().get().getId())
                        .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
                        .map(answerSurveyTO -> POST_BINDER.bindPostWithAnswer(post, answerSurveyTO))
                        .switchIfEmpty(just(POST_BINDER.bind(post))))
                .switchIfEmpty(error(EntityNotFoundException::new));
    }

    @Override
    public Mono<Void> delete(@NotNull String id, FySelfContext context) {
        return repository.findById(id)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .flatMap(post -> repository.softDelete(post).doOnSuccess(entity -> deleteEvent(post, context)))
                .then();
    }

    @Override
    public Mono<PostTO> patch(@NotNull String id, HashMap to, FySelfContext context) {
        return repository.findById(id)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(post -> POST_BINDER.pacth(post, to));
    }

    @Override
    public Mono<PagedList<PostTO>> search(@NotNull PostCriteriaTO criteria, FySelfContext context) {
        return repository.findPage(POST_BINDER.bindToCriteria(criteria.withOwner(context.getAccount().get().getId())))
                .map(POST_BINDER::bindPage)
                .flatMap(postTOPagedList -> fromIterable(postTOPagedList.getElements())
                        .flatMap(postTO -> answerSurveyRepository.findByPostAndUser(postTO.getId(), context.getAccount().get().getId())
                                .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
                                .map(answerSurveyTO -> POST_BINDER.bindPostTOWithAnswer(postTO, answerSurveyTO))
                                .switchIfEmpty(just(postTO)))
                        .collectList()
                        .map(postTOS -> POST_BINDER.bind(postTOPagedList, postTOS)));
    }

    @Override
    public Mono<Void> block(String post) {
        return repository.findById(post)
                .map(POST_BINDER::bindBlocked)
                .flatMap(repository::save)
                .then();
    }

    @Override
    public Mono<Void> shareWith(@NotNull PostShareTO to, FySelfContext context) {
        return repository.findById(to.getPost())
                .flatMap(post -> repository.save(POST_BINDER.bindShareWith(post, to)))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<Void> stopShareWith(@NotNull PostShareTO to, FySelfContext context) {
        return repository.findById(to.getPost())
                .flatMap(post -> repository.save(POST_BINDER.bindStopShareWith(post, to)))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<PagedList<PostTO>> searchMe(PostTimelineCriteriaTO criteria, FySelfContext context) {
        return repository.findPage(POST_BINDER.bindToCriteria(criteria.withUser(context.getAccount().get().getId())))
                .map(POST_BINDER::bindPage);
    }
}
