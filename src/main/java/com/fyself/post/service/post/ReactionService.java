package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.ReactionTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.domain.enums.ReactionType;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


/**
 * Service for Post Reaction.
 *
 * @author Yero
 * @since 0.0.1
 */
public interface ReactionService {
    Mono<String> add(@Valid ReactionTO to, FySelfContext context);
    Mono<Void> update(@Valid ReactionTO to, FySelfContext context);
    Mono<Void> delete(String post, FySelfContext context);
    Mono<PagedList<PostReportTO>> loadAll(PostReportCriteriaTO criteria, FySelfContext context);
    Mono<ReactionType> meReaction(String post, FySelfContext context);
}
