package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.PostShareBulkTO;
import com.fyself.post.service.post.contract.to.PostShareTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.criteria.PostCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;


/**
 * Service for Post.
 *
 * @author jmmarin
 * @since 0.1.0
 */
public interface PostService {

    Mono<String> create(@NotNull @Valid PostTO to, FySelfContext context);

    Mono<Void> update(@NotNull @Valid PostTO to, FySelfContext context);

    Mono<PostTO> load(@NotNull String id, FySelfContext context);

    Mono<Void> delete(@NotNull String id, FySelfContext context);

    Mono<PostTO> patch(@NotNull String id, HashMap to, FySelfContext context);

    Mono<PagedList<PostTO>> search(@NotNull PostCriteriaTO criteria, FySelfContext context);

    Mono<Void> block(String post);

    Mono<Void> shareWith(@NotNull PostShareTO to, FySelfContext context);
    Mono<Void> sharePost(@NotNull PostShareBulkTO to, FySelfContext context);
    Mono<Void> stopShareWith(@NotNull PostShareTO to, FySelfContext context);

    Mono<PagedList<PostTO>> searchMe(PostTimelineCriteriaTO criteria, FySelfContext context);

    Mono<Void> create(PostTO to);

    /*PostTO bindPostTO(Post post);*/

    Mono<PostTO> addSharedPostContent(PostTO postTO);
}
