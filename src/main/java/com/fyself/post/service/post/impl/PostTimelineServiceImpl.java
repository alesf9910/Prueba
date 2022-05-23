package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.PostTimelineService;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.post.service.post.contract.to.SharedPostTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.post.service.post.datasource.PostTimelineRepository;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.PostTimeline;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;
import com.fyself.post.service.post.datasource.domain.subentities.SharedPost;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.fyself.post.service.post.contract.AnswerSurveyBinder.ANSWER_SURVEY_BINDER;
import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.service.post.contract.PostTimelineBinder.POST_TIMELINE_BINDER;
import static java.util.stream.Collectors.toList;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

@Service("postTimelineService")
@Validated
public class PostTimelineServiceImpl implements PostTimelineService {
    private final PostTimelineRepository repository;
    final PostService postService;
    final AnswerSurveyRepository answerSurveyRepository;
    private final PostRepository postRepository;

    public PostTimelineServiceImpl(PostTimelineRepository repository,
                                   AnswerSurveyRepository answerSurveyRepository,
                                   PostService postService,
                                   PostRepository postRepository) {
        this.repository = repository;
        this.answerSurveyRepository = answerSurveyRepository;
        this.postService = postService;
        this.postRepository = postRepository;
    }

    @Override
    public Mono<String> create(@NotNull @Valid PostTimelineTO to) {
        return repository.findAllByUserAndPost(to.getUser(), to.getPost())
                .flatMap(count -> {
                    if (count == 0)
                        return repository.save(POST_TIMELINE_BINDER.bind(to.withCreatedAt().withUpdatedAt()))
                                .map(PostTimeline::getUser);
                    return just(to.getUser());
                })
                .switchIfEmpty(error(EntityNotFoundException::new));
    }

    @Override
    public Mono<PagedList<PostTO>> search(PostTimelineCriteriaTO criteria, FySelfContext context) {
        return repository.findPage(POST_BINDER.bindToTimelineCriteria(criteria.withUser(context.getAccount().get().getId())))
                .map(postTimelines ->
                        POST_BINDER.bindPageTimeline(postTimelines, context.getAccount().get().getId()))
                .flatMap(postTOPagedList -> fromIterable(postTOPagedList.getElements())
                        .flatMapSequential(postTO ->
                            answerSurveyRepository.findByPostAndUser(
                                postTO.getContent() != null ?
                                    postTO.getContent().getTypeContent() != null ?
                                        postTO.getContent().getTypeContent() == TypeContent.SHARED_POST?
                                            ((SharedPostTO) postTO.getContent()).getPostTo().getId() :
                                            postTO.getId()
                                        : postTO.getId()
                                    : postTO.getId()
                                ,
                                context.getAccount().get().getId())
                                .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
                                .map(answerSurveyTO -> POST_BINDER.bindPostTOWithAnswer(postTO, answerSurveyTO))
                                .switchIfEmpty(just(postTO)), 1)
                        .flatMapSequential(posTO -> findSharedPosts(posTO))
                        .collectList()
                        .map(postTOS -> POST_BINDER.bind(postTOPagedList, postTOS)));
    }

    private Mono<PostTO> findSharedPosts(PostTO postTO) {
        Set<String> idsSharedPosts = new HashSet<>();
        String idPost;
        if (postTO.getContent().getTypeContent() != null && postTO.getContent().getTypeContent().equals(TypeContent.SHARED_POST))
            idPost = ((SharedPostTO) postTO.getContent()).getPostTo().getId();
        else
            idPost = postTO.getId();
        return postRepository.findAllShared(idPost)
                .collectList()
                .map(listSharedPosts -> {
                    for (Post sharedPost: listSharedPosts)
                        idsSharedPosts.add(sharedPost.getId());
                    return postTO.putSharedPosts(idsSharedPosts);
                })
                .doOnError(err -> {throw new EntityNotFoundException(err.getMessage());});
    }
}
