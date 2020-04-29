package com.fyself.post.facade;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

/**
 * Facade interface for <i>Post report</i> operations.
 *
 * @author Alejandro
 * @since 0.0.1
 */
public interface PostReportFacade {
    Mono<Result<String>> create(PostReportTO packageTemplate, FySelfContext exchange);
    Mono<Result<Void>> update(PostReportTO packageTemplate, FySelfContext context);
    Mono<Result<Void>> delete(String id, FySelfContext context);
    Mono<Result<PostReportTO>> load(String id, FySelfContext context);
    Mono<Result<PagedList<PostReportTO>>> search(PostReportCriteriaTO criteria, FySelfContext context);
    Mono<Result<PagedList<PostReportTO>>> searchByMe(PostReportCriteriaTO criteria, FySelfContext context);
    Mono<Result<PagedList<PostReportTO>>> searchToMe(PostReportCriteriaTO criteria, FySelfContext context);
}
