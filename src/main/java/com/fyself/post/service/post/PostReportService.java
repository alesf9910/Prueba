package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.stream.DoubleStream;


/**
 * Service for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
public interface PostReportService {

    Mono<String> add(@Valid PostReportTO to, FySelfContext context);

    Mono<Void> update(PostReportTO to, FySelfContext context);

    Mono<Void> delete(String id, FySelfContext context);

    Mono<PostReportTO> load(String id, FySelfContext context);

    Mono<PagedList<PostReportTO>> loadAll(PostReportCriteriaTO criteria, FySelfContext context);

    Mono<Long> countAllByPost(String post);
}
