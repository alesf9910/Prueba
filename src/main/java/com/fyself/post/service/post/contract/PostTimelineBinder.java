package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.post.service.post.datasource.domain.Post;
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

    default PostTimeline bind(Post post) {
        var postTimeline = new PostTimeline();
        postTimeline.setPost(post);
        postTimeline.setUser(post.getOwner());
        postTimeline.setCreatedAt(post.getCreatedAt());
        postTimeline.setCreatedAt(post.getUpdatedAt());
        return postTimeline;
    }
}
