package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.PostCommentTimelineTO;
import com.fyself.post.service.post.datasource.domain.Comment;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.PostCommentTimeline;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Binder
 *
 * @author jmmarin
 * @since 0.0.1
 */
@Mapper
public interface PostCommentTimelineBinder {
    PostCommentTimelineBinder POST_COMMENT_TIMELINE_BINDER = Mappers.getMapper(PostCommentTimelineBinder.class);

    @Mapping(target = "post.id", source = "post")
    @Mapping(target = "comment.id", source = "comment")
    PostCommentTimeline bind(PostCommentTimelineTO source);

    default PostCommentTimeline bind(Comment comment) {
        var postCommentTimeline = new PostCommentTimeline();
        postCommentTimeline.setComment(comment);
        postCommentTimeline.setUser(comment.getOwner());
        postCommentTimeline.setCreatedAt(comment.getCreatedAt());
        postCommentTimeline.setCreatedAt(comment.getUpdatedAt());
        return postCommentTimeline;
    }
}