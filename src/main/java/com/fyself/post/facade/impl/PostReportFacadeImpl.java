package com.fyself.post.facade.impl;

import com.fyself.post.facade.PostReportFacade;
import com.fyself.post.service.post.PostReportService;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.seedwork.facade.Result;
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
public class PostReportFacadeImpl implements PostReportFacade {

    final PostReportService service;

    public PostReportFacadeImpl(PostReportService service) {
        this.service = service;
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
    public Mono<Result<Void>> delete(String id, String post, FySelfContext context) {
        return service.delete(id, post, context).thenReturn(successful());
    }

    @Override
    public Mono<Result<PostReportTO>> load(String id, String post, FySelfContext context) {
        return service.load(id, post, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<PostReportTO>>> search(PostReportCriteriaTO criteria, String post, FySelfContext context) {
        return service.loadAll(criteria, post, context).map(Result::successful);
    }
}
