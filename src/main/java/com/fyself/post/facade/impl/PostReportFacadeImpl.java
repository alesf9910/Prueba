package com.fyself.post.facade.impl;

import com.fyself.post.facade.PostReportFacade;
import com.fyself.post.service.post.PostReportService;
import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.facade.Result.successful;

/**
 * Facade implementation for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Facade("postReportFacade")
public class PostReportFacadeImpl implements PostReportFacade {

    final PostReportService service;
    final PostService postService;

    public PostReportFacadeImpl(PostReportService service, PostService postService) {
        this.service = service;
        this.postService = postService;
    }

    @Override
    public Mono<Result<String>> create(PostReportTO to, FySelfContext exchange) {
        return service.add(to, exchange).map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> update(PostReportTO to, FySelfContext context) {
        return service.update(to, context).thenReturn(successful());
    }

    @Override
    public Mono<Result<Void>> delete(String id, FySelfContext context) {
        return service.delete(id, context).thenReturn(successful());
    }

    @Override
    public Mono<Result<PostReportTO>> load(String id, FySelfContext context) {
        return service.load(id, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<PostReportTO>>> search(PostReportCriteriaTO criteria, FySelfContext context) {
        return service.loadAll(criteria, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<PostReportTO>>> searchByMe(PostReportCriteriaTO criteria, FySelfContext context) {
        return service.loadAll(criteria.withOwner(context.getAccount().get().getId()), context).map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<PostReportTO>>> searchToMe(PostReportCriteriaTO criteria, FySelfContext context) {
        return service.loadAll(criteria.withUser(context.getAccount().get().getId()), context).map(Result::successful);
    }
}
