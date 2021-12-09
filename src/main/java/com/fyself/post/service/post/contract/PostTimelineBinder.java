package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.PostTimeline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;
import java.util.Set;

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
        postTimeline.setUpdatedAt(post.getUpdatedAt());
        postTimeline.setEnterprise(post.getEnterprise());
        postTimeline.setWorkspace(post.getWorkspace());
        return postTimeline;
    }
    default PostTimeline bindU(Post post, String sharedW) {
        var postTimeline = new PostTimeline();
        postTimeline.setPost(post);
        postTimeline.setUser(sharedW);
        postTimeline.setOwner(post.getOwner());
        postTimeline.setCreatedAt(post.getCreatedAt());
        postTimeline.setUpdatedAt(post.getUpdatedAt());
        postTimeline.setEnterprise(post.getEnterprise());
        postTimeline.setWorkspace(post.getWorkspace());
        return postTimeline;
    }
}
