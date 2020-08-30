package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.PostShareBulkTO;
import com.fyself.post.service.post.contract.to.PostShareTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.criteria.PostCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.post.service.post.datasource.PostTimelineRepository;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.subentities.SharedPost;
import com.fyself.post.service.stream.StreamService;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.ValidationException;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;

import static com.fyself.post.service.post.contract.AnswerSurveyBinder.ANSWER_SURVEY_BINDER;
import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.service.post.contract.PostTimelineBinder.POST_TIMELINE_BINDER;
import static com.fyself.post.tools.LoggerUtils.*;
import static java.util.stream.Collectors.toList;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

@Service("postService")
@Validated
public class PostServiceImpl implements PostService {
    final PostRepository repository;
    final PostTimelineRepository postTimelineRepository;
    final AnswerSurveyRepository answerSurveyRepository;
    final StreamService streamService;

    public PostServiceImpl(PostRepository repository, PostTimelineRepository postTimelineRepository, AnswerSurveyRepository answerSurveyRepository, StreamService streamService) {
        this.repository = repository;
        this.postTimelineRepository = postTimelineRepository;
        this.answerSurveyRepository = answerSurveyRepository;
        this.streamService = streamService;
    }

    @Override
    public Mono<String> create(@NotNull @Valid PostTO to, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> createPost(POST_BINDER.bind(to.withUserId(userId).withCreatedAt().withUpdatedAt()),context))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }

    @Override
    public Mono<Void> update(@NotNull @Valid PostTO to, FySelfContext context) {
        return repository.getById(to.getId())
                .map(post -> POST_BINDER.set(post, to.withUpdatedAt()))
                .flatMap(post -> repository.save(post)
                        .doOnSuccess(entity -> updateEvent(entity, context))
                        .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)).subscribe())
                )
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<PostTO> load(@NotNull String id, FySelfContext context) {
        return repository.getById(id)
                            .flatMap(post -> (post.getContent() instanceof SharedPost)
                                    ?repository.getById(((SharedPost) post.getContent()).getPost())
                                                .map(fatherPost -> POST_BINDER.bindSharedPostContent(fatherPost,post))
                                                .flatMap(sharedPost -> loadPost(sharedPost,context))
                                    :loadPost(post,context))
                            .switchIfEmpty(error(EntityNotFoundException::new));
    }


    @Override
    public Mono<Void> delete(@NotNull String id, FySelfContext context) {
        return repository.getById(id)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .flatMap(post -> repository.softDelete(post)
                        .doOnSuccess(entity -> deleteEvent(post, context))
                        .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)).subscribe())
                        .doOnSuccess(entity -> postTimelineRepository.deleteAllByPost_IdAndUser(entity.getId(),entity.getOwner()).subscribe())
                )
                .then();
    }

    @Override
    public Mono<PostTO> patch(@NotNull String id, HashMap to, FySelfContext context) {
        return repository.getById(id)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(post -> POST_BINDER.pacth(post, to));
    }

    @Override
    public Mono<PagedList<PostTO>> search(@NotNull PostCriteriaTO criteria, FySelfContext context) {
        return
                repository.findPage(POST_BINDER.bindToCriteria(criteria.withOwner(context.getAccount().get().getId())))
                .map(this::bindPage)
                .flatMap(postTOPagedList -> fromIterable(postTOPagedList.getElements())
                        .flatMap(
                                postTO ->
                                        answerSurveyRepository.findByPostAndUser(postTO.getId(), context.getAccount().get().getId())
                                .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
                                .map(answerSurveyTO -> POST_BINDER.bindPostTOWithAnswer(postTO, answerSurveyTO))
                                .switchIfEmpty(just(postTO)), 1)
                        .collectList()
                        .map(postTOS -> POST_BINDER.bind(postTOPagedList, postTOS)));
    }

    private Mono<PostTO> loadPost(Post to, FySelfContext context){
        return answerSurveyRepository.findByPostAndUser(to.getId(), context.getAccount().get().getId())
                .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
                .map(answerSurveyTO -> POST_BINDER.bindPostWithAnswer(to, answerSurveyTO))
                .switchIfEmpty(just(POST_BINDER.bind(to)));
    }

    @Override
    public Mono<Void> block(String post) {
        return repository.findById(post)
                .map(POST_BINDER::bindBlocked)
                .flatMap(repository::save)
                .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)))
                .then();
    }

    @Override
    public Mono<Void> shareWith(@NotNull PostShareTO to, FySelfContext context) {
        return repository.findById(to.getPost())
                .flatMap(post -> repository.save(POST_BINDER.bindShareWith(post, to)))
                .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)).subscribe())
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<Void> sharePost(@NotNull PostShareBulkTO to, FySelfContext context) {
        return repository.findById(to.getPost())
                .flatMap(post -> {
                    if(post.getOwner().equals(context.getAccount().get().getId())) {
                        return shareBulk(POST_BINDER.bindShareBulk(post, to), context);
                    } else {
                        if(post.getAccess().equals(Access.PUBLIC)){
                            return createPost(POST_BINDER.bindSharedPost(post,context.getAccount().get().getId()),context).then(just(true));
                        } else {
                            return error(ValidationException::new);
                        }
                    }
                })
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<Void> stopShareWith(@NotNull PostShareTO to, FySelfContext context) {
        return repository.findById(to.getPost())
                .flatMap(post -> repository.save(POST_BINDER.bindStopShareWith(post, to)))
                .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)).subscribe())
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<PagedList<PostTO>> searchMe(PostTimelineCriteriaTO criteria, FySelfContext context) {
        return repository.findPage(POST_BINDER.bindToCriteria(criteria.withUser(context.getAccount().get().getId())))
                .map(this::bindPage);
    }

    @Override
    public Mono<Void> create(PostTO to) {
        return repository.save(POST_BINDER.bind(to.withUserId(to.getOwner()).withCreatedAt().withUpdatedAt()))
                .flatMap(post -> postTimelineRepository.save(POST_TIMELINE_BINDER.bind(post)).thenReturn(post))
                .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)).subscribe())
                .doOnSuccess(entity -> createEvent(entity, to.getOwner()))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }


    private Mono<Boolean> shareBulk(@NotNull Post to, FySelfContext context) {
            return repository.save(to)
                    .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)).subscribe())
                    .then(just(true));
    }

    private Mono<Post> createPost(@NotNull Post to, FySelfContext context){
        return repository.save(to)
                .flatMap(post -> postTimelineRepository.save(POST_TIMELINE_BINDER.bind(post)).thenReturn(post))
                .doOnSuccess(entity -> createEvent(entity, context))
                .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)).subscribe());
    }

    private PagedList<PostTO> bindPage(Page<Post> source) {
        List<PostTO> postTOS = source.stream().map(post -> (post.getContent() instanceof SharedPost)
                ?POST_BINDER.bind(repository.getById(((SharedPost) post.getContent()).getPost()).map(fatherPost -> POST_BINDER.bindSharedPostContent(fatherPost,post)).block())
                :POST_BINDER.bind(post)).collect(toList());
        return new PagedList<>(postTOS, source.getNumber(), source.getTotalPages(), source.getTotalElements());
    }



}
