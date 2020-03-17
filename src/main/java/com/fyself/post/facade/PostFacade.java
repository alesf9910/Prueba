package com.fyself.post.facade;

import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * Facade interface for <i>Post</i> operations.
 *
 * @author jmmarin
 * @since 0.0.2
 */
public interface PostFacade {
    Mono<Result<String>> create(PostTO to, FySelfContext context);

    Mono<Result<PostTO>> load(String post, FySelfContext context);

    Mono<Result<Void>> delete(String post, FySelfContext context);

    Mono<Result<Void>> update(PostTO to, FySelfContext context);

    Mono<Result<Void>> patch(String id, HashMap to, FySelfContext context);

}
