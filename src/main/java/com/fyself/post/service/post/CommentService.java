package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.CommentTO;
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

//    Mono<Void> update(@NotNull @Valid PostTO to, FySelfContext context);
//

//
//    Mono<Void> delete(@NotNull String id, FySelfContext context);

}
