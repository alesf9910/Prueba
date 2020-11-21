package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.PostCommentTimelineTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
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
public interface PostCommentTimelineService {

    Mono<String> create(@NotNull @Valid PostCommentTimelineTO to);

    /*Mono<PagedList<PostTO>> search(PostTimelineCriteriaTO criteria, FySelfContext context);*/

}
