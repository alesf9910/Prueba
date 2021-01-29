package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.ReactionTO;
import com.fyself.post.service.post.datasource.domain.enums.ReactionType;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Map;


/**
 * Service for Post Reaction.
 *
 * @author Yero
 * @since 0.0.1
 */
public interface ReactionService {
    Mono<String> add(@Valid ReactionTO to, FySelfContext context);
    Mono<String> addWS(@Valid ReactionTO to, FySelfContext context);
    Mono<Void> update(@Valid ReactionTO to, FySelfContext context);
    Mono<Void> delete(String post, FySelfContext context);
    Mono<Map> loadAll(String criteria, FySelfContext context);
    Mono<ReactionType> meReaction(String post, FySelfContext context);
}
