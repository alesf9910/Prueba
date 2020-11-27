package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.contract.to.criteria.CommentCriteriaTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Service for Comment.
 *
 * @author jmmarin
 * @since 0.1.0
 */
public interface CommentService {

    Mono<String> add(@NotNull @Valid CommentTO to, FySelfContext context);

    Mono<CommentTO> load(@NotNull String id, String post, FySelfContext context);

    Mono<Void> update(@NotNull @Valid CommentTO to, FySelfContext context);

    Mono<Void> delete(@NotNull String id, String post, FySelfContext context);

    Mono<PagedList<CommentTO>> search(@NotNull CommentCriteriaTO criteria, String post, FySelfContext context);

    Mono<PagedList<CommentTO>> searchBefore(@NotNull CommentCriteriaTO criteria, String post, String id, FySelfContext context);

    Mono<Long> count(String post);

}
