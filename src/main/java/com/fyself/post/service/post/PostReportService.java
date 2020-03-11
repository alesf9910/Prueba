package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Service for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
public interface PostReportService {

    Mono<String> add(@NotNull @Valid PostReportTO to, FySelfContext context);

    Mono<Void> update(@NotNull @Valid PostReportTO to, FySelfContext context);

    Mono<Void> delete(@NotNull String id, String post, FySelfContext context);

    Mono<PostReportTO> load(@NotNull String id, String post, FySelfContext context);

    Mono<PagedList<PostReportTO>> loadAll(PostReportCriteriaTO criteria, String post, FySelfContext context);

    Mono<PagedList<PostReportTO>> loadAll(PostReportCriteriaTO criteria, FySelfContext context);

}
