package com.fyself.post.facade.impl;

import com.fyself.post.facade.PostFacade;
import com.fyself.post.service.post.CommentService;
import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.PostTimelineService;
import com.fyself.post.service.post.ReactionService;
import com.fyself.post.service.post.contract.to.PostShareBulkTO;
import com.fyself.post.service.post.contract.to.PostShareTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.SharedPostTO;
import com.fyself.post.service.post.contract.to.criteria.PostCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.enums.TypeSearch;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.seedwork.facade.Result.successful;
import static reactor.core.publisher.Mono.error;

@Facade("postFacade")
public class PostFacadeImpl implements PostFacade {

    private final PostService service;
    private final PostTimelineService postTimelineService;
    private final CommentService commentService;
    private final ReactionService reactionService;

    public PostFacadeImpl(PostService service, PostTimelineService postTimelineService, CommentService commentService, ReactionService reactionService) {
        this.service = service;
        this.postTimelineService = postTimelineService;
        this.commentService = commentService;
        this.reactionService = reactionService;
    }

    @Override
    public Mono<Result<String>> create(PostTO to, FySelfContext context) {
        if(!to.getWorkspace())
            return service.create(to, context).map(Result::successful);
        else
            return service.createPostWorkspace(to, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PostTO>> load(String post, FySelfContext context) {
        return service.load(post, context)
                .flatMap(postTO -> {
                    if(postTO.getContent() instanceof SharedPostTO){
                        return service.addSharedPostContent(postTO);
                    } else
                    {
                        return Mono.just(postTO);
                    }
                })
                .flatMap(postTOResult -> commentService.count(post).map(postTOResult::putCount))
                .flatMap(postTOResult -> reactionService.meReaction(post,context).map(postTOResult::putReaction).switchIfEmpty(Mono.just(postTOResult)))
                .flatMap(postTOResult -> reactionService.loadAll(post,context)./*filter(map -> !(postTOResult.getOwner().equals(context.getAccount().get().getId()))).*/map(postTOResult::putReactionStats).switchIfEmpty(Mono.just(postTOResult)))
                .map(postTO -> this.updateOwnerWhenShared(postTO, context))
                .map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> delete(String post, FySelfContext context) {
        return service.delete(post, context).thenReturn(successful());
    }

    @Override
    public Mono<Result<Void>> update(PostTO to, FySelfContext context) {
        return service.update(to, context).thenReturn(successful());
    }

    @Override
    public Mono<Result<Void>> patch(String id, HashMap to, FySelfContext context) {
        return service.patch(id, to, context)
                .thenReturn(successful());
    }

    @Override
    public Mono<Result<PagedList<PostTO>>> search(PostCriteriaTO criteria, FySelfContext context) {
        return service.search(criteria, context)
                .flatMap(page -> putExtraData(page, context))
                .map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<PostTO>>> searchByEnterprise(PostCriteriaTO criteria, FySelfContext context) {
        return service.searchByEnterprise(criteria, context)
                .flatMap(page -> putExtraData(page, context))
                .map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<PostTO>>> searchPostTimeline(PostTimelineCriteriaTO criteria, FySelfContext context) {
        if (criteria.getType() == TypeSearch.ALL)
            return postTimelineService.search(criteria, context)
                    .flatMap(page -> putExtraData(page, context))
                    .map(Result::successful);
        else
            return service.search(POST_BINDER.bindToCriteriaTO(criteria), context)
                    .flatMap(page -> putExtraData(page, context))
                    .map(Result::successful);
    }

    private Mono<PagedList<PostTO>> putExtraData(PagedList<PostTO> page, FySelfContext context) {
        return Flux.fromIterable(page.getElements())
                .flatMap(postTOResult -> commentService.count(postTOResult.getId()).map(postTOResult::putCount), 1)
                .flatMap(post -> reactionService.meReaction(post.getId(),context).map(post::putReaction).switchIfEmpty(Mono.just(post)),1)
                .flatMap(post -> reactionService.loadAll(post.getId(),context).map(post::putReactionStats).switchIfEmpty(Mono.just(post)))
                .flatMap(postTO -> {
                    if(postTO.getContent() instanceof SharedPostTO){
                        return service.addSharedPostContent(postTO);
                    } else
                    {
                        return Mono.just(postTO);
                    }
                })
                .collectList()
                .map(postTOS -> this.updateOwnerWhenShared(postTOS, context))
                .map(elements -> {page.setElements(elements); return page;});
    }

    private List<PostTO> updateOwnerWhenShared(List<PostTO> listPostTO, FySelfContext context)
    {
        return listPostTO.stream().peek(postTO -> {
            if(postTO.getContent().getTypeContent() == TypeContent.SHARED_POST && !(postTO.getOwner().equals(context.getAccount().get().getId())))
            {
                String owner = postTO.getOwner();
                postTO.setSharedBy(owner);
                postTO.setOwner(((SharedPostTO)postTO.getContent()).getPostTo().getOwner());
            }
        }).collect(Collectors.toList());

    }

    private PostTO updateOwnerWhenShared(PostTO postTO, FySelfContext context)
    {

        if(postTO.getContent().getTypeContent() == TypeContent.SHARED_POST && !(postTO.getOwner().equals(context.getAccount().get().getId())))
        {
            String owner = postTO.getOwner();
            postTO.setSharedBy(owner);
            postTO.setOwner(((SharedPostTO)postTO.getContent()).getPostTo().getOwner());
        }
        return postTO;
    }

    @Override
    public Mono<Result<Void>> shareWith(PostShareTO to, FySelfContext context) {
        return service.shareWith(to, context).thenReturn(successful());
    }

    @Override
    public Mono<Result<String>> shareBulk(PostShareBulkTO to, FySelfContext context) {
        return service.sharePost(to, context).map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> stopShareWith(PostShareTO to, FySelfContext context) {
        return service.stopShareWith(to, context).thenReturn(successful());
    }

    @Override
    public Mono<Void> unpinnedPost(Map map){
        return service.unpinnedPost(map.get("userId").toString());
    }
}
