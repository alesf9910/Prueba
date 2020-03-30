package com.fyself.post.facade.impl;

import com.fyself.post.facade.PostFacade;
import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.PostTimelineService;
import com.fyself.post.service.post.contract.to.PostShareTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.criteria.PostCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import java.util.HashMap;

import static com.fyself.seedwork.facade.Result.successful;

@Facade("postFacade")
public class PostFacadeImpl implements PostFacade {

    private final PostService service;
    private final PostTimelineService postTimelineService;

    public PostFacadeImpl(PostService service, PostTimelineService postTimelineService) {
        this.service = service;
        this.postTimelineService = postTimelineService;
    }

    @Override
    public Mono<Result<String>> create(PostTO to, FySelfContext context) {
        return service.create(to, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PostTO>> load(String post, FySelfContext context) {
        return service.load(post, context).map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> delete(String post, FySelfContext context) {
        return service.delete(post, context).map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> update(PostTO to, FySelfContext context) {
        return service.update(to, context).thenReturn(successful());
    }

    @Override
    public Mono<Result<Void>> patch(String id, HashMap to, FySelfContext context) {
        return service.patch(id, to, context)
                .flatMap(postTO -> service.update(postTO, context))
                .thenReturn(successful());
    }

    @Override
    public Mono<Result<PagedList<PostTO>>> search(PostCriteriaTO criteria, FySelfContext context) {
        return service.search(criteria, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<PostTO>>> searchPostTimeline(PostTimelineCriteriaTO criteria, FySelfContext context) {
        return Mono.fromSupplier(() -> criteria)
                .filter(PostTimelineCriteriaTO::isMe)
                .flatMap(criteriaTO -> service.searchMe(criteriaTO, context))
                .switchIfEmpty(postTimelineService.search(criteria, context))
                .map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> shareWith(PostShareTO to, FySelfContext context) {
        return service.shareWith(to, context).thenReturn(successful());
    }

    @Override
    public Mono<Result<Void>> stopShareWith(PostShareTO to, FySelfContext context) {
        return service.stopShareWith(to, context).thenReturn(successful());
    }
}
