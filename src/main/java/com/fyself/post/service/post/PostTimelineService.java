package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Service for Post.
 *
 * @author jmmarin
 * @since 0.1.0
 */
public interface PostTimelineService {

    Mono<String> create(@NotNull @Valid PostTimelineTO to);

    Mono<PagedList<PostTO>> search(FySelfContext context);

}
