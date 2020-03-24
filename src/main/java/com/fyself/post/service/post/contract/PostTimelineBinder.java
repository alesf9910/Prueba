package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.post.service.post.datasource.domain.PostTimeline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import static java.time.LocalDateTime.now;

/**
 * Binder
 *
 * @author jmmarin
 * @since 0.0.1
 */
@Mapper
public interface PostTimelineBinder {
    PostTimelineBinder POST_TIMELINE_BINDER = Mappers.getMapper(PostTimelineBinder.class);

    @Mapping(target = "post.id", source = "post")
    PostTimeline bind(PostTimelineTO source);

    default PostTimelineTO bin(String userId, String post, String owner) {
        var to = new PostTimelineTO();
        to.setOwner(owner);
        to.setCreatedAt(now());
        to.setUpdatedAt(now());
        to.setPost(post);
        to.setUser(userId);
        return to;
    }
}
