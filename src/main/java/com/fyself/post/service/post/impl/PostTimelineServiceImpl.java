package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.PostTimelineService;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.post.service.post.contract.to.SharedPostTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.PostTimelineRepository;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.PostTimeline;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;
import com.fyself.post.service.post.datasource.domain.subentities.SharedPost;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;

import static com.fyself.post.service.post.contract.AnswerSurveyBinder.ANSWER_SURVEY_BINDER;
import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.service.post.contract.PostTimelineBinder.POST_TIMELINE_BINDER;
import static java.util.stream.Collectors.toList;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.just;

@Service("postTimelineService")
@Validated
public class PostTimelineServiceImpl implements PostTimelineService {
    private final PostTimelineRepository repository;
    final PostService postService;
    final AnswerSurveyRepository answerSurveyRepository;


    public PostTimelineServiceImpl(PostTimelineRepository repository, AnswerSurveyRepository answerSurveyRepository,PostService postService) {
        this.repository = repository;
        this.answerSurveyRepository = answerSurveyRepository;
        this.postService = postService;
    }

    @Override
    public Mono<String> create(@NotNull @Valid PostTimelineTO to) {
        repository.findAllByUserAndPost(to.getUser(), to.getPost()).map(count -> {
            if (count == 0)
                return repository.save(POST_TIMELINE_BINDER.bind(to.withCreatedAt().withUpdatedAt()))
                        .map(PostTimeline::getUser);
           return to.getUser();
        }).subscribe();
        return just(to.getUser());
    }

    @Override
    public Mono<PagedList<PostTO>> search(PostTimelineCriteriaTO criteria, FySelfContext context) {
        return repository.findPage(POST_BINDER.bindToTimelineCriteria(criteria.withUser(context.getAccount().get().getId())))
                .map(postTimelines ->
                        POST_BINDER.bindPageTimeline(postTimelines, context.getAccount().get().getId()))
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
    }
}
