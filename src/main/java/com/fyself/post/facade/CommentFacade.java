package com.fyself.post.facade;

import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

/**
 * Facade interface for <i>Comment</i> operations.
 *
 * @author jmmarin
 * @since 0.0.2
 */
public interface CommentFacade {
    Mono<Result<String>> create(CommentTO to, FySelfContext context);
    Mono<Result<CommentTO>> load(String id, String post, FySelfContext context);

}
