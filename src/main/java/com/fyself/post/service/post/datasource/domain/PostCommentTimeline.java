package com.fyself.post.service.post.datasource.domain;


import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import com.fyself.seedwork.service.repository.mongodb.stereotype.CascadeReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "post-comment-timeline")
public class PostCommentTimeline extends DomainAuditEntity {

    private static final long serialVersionUID = -1974933551312804543L;
    @DBRef
    @CascadeReference
    private Post post;
    @CascadeReference
    private Comment comment;
    private String user;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public static Post getPostModified(PostTimeline postTimeline) {
        Post p = postTimeline.getPost();
        p.setCreatedAt(postTimeline.getCreatedAt());
        return p;

    }

    public static Comment getPostCommentModified(PostCommentTimeline postCommentTimeline) {
        Comment c = postCommentTimeline.getComment();
        c.setCreatedAt(postCommentTimeline.getCreatedAt());
        return c;

    }
}