package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.PostTimelineTO;
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

}
