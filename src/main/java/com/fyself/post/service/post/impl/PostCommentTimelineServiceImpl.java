package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostCommentTimelineService;
import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.PostCommentTimelineTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.post.service.post.contract.to.SharedPostTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.PostCommentTimelineRepository;
import com.fyself.post.service.post.datasource.domain.PostCommentTimeline;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.fyself.post.service.post.contract.AnswerSurveyBinder.ANSWER_SURVEY_BINDER;
import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.service.post.contract.PostCommentTimelineBinder.POST_COMMENT_TIMELINE_BINDER;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.just;

@Service("postCommentTimelineService")
@Validated
public class PostCommentTimelineServiceImpl implements PostCommentTimelineService {
    private final PostCommentTimelineRepository repository;
    final PostService postService;
    final AnswerSurveyRepository answerSurveyRepository;


    public PostCommentTimelineServiceImpl(PostCommentTimelineRepository repository, AnswerSurveyRepository answerSurveyRepository,PostService postService) {
        this.repository = repository;
        this.answerSurveyRepository = answerSurveyRepository;
        this.postService = postService;
    }

    @Override
    public Mono<String> create(@NotNull @Valid PostCommentTimelineTO to) {
        return repository
                .save(POST_COMMENT_TIMELINE_BINDER.bind(to.withCreatedAt().withUpdatedAt()))
                .map(PostCommentTimeline::getUser);
    }

    /*@Override
    public Mono<PagedList<PostTO>> search(PostTimelineCriteriaTO criteria, FySelfContext context) {
        return repository.findPage(POST_BINDER.bindToTimelineCriteria(criteria.withUser(context.getAccount().get().getId())))
                .map(postTimelines -> POST_BINDER.bindPageTimeline(postTimelines, context.getAccount().get().getId()))
                .flatMap(postTOPagedList -> fromIterable(postTOPagedList.getElements())
                        .flatMap(postTO ->
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

                        .collectList()

                        .map(postTOS -> POST_BINDER.bind(postTOPagedList, postTOS)));
    }*/
}
