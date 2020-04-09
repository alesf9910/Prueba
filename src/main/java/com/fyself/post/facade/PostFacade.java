package com.fyself.post.facade;

import com.fyself.post.service.post.contract.to.PostShareBulkTO;
import com.fyself.post.service.post.contract.to.PostShareTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.criteria.PostCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.PagedList;
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

    Mono<Result<PagedList<PostTO>>> search(PostCriteriaTO criteria, FySelfContext context);

    Mono<Result<PagedList<PostTO>>> searchPostTimeline(PostTimelineCriteriaTO criteria, FySelfContext context);

    Mono<Result<Void>> shareWith(PostShareTO to, FySelfContext context);
    Mono<Result<Void>> shareBulk(PostShareBulkTO to, FySelfContext context);
    Mono<Result<Void>> stopShareWith(PostShareTO to, FySelfContext context);
}
