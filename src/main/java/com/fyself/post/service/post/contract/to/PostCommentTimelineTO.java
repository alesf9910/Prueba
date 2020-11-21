package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class PostCommentTimelineTO extends DomainAuditTransferObject {

    private static final long serialVersionUID = -8526218291708837581L;

    private String post;
    private String comment;
    private String user;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    @ReadOnly
    public String getOwner() {
        return super.getOwner();
    }

    @Override
    public void setOwner(String owner) {
        super.setOwner(owner);
    }

    @Override
    @ReadOnly
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        super.setCreatedAt(createdAt);
    }

    @Override
    @ReadOnly
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        super.setUpdatedAt(updatedAt);
    }

    public static PostCommentTimelineTO from(String userId, String post, String comment, String owner) {
        var to = new PostCommentTimelineTO();
        to.setOwner(owner);
        to.setCreatedAt(now());
        to.setUpdatedAt(now());
        to.setPost(post);
        to.setComment(comment);
        to.setUser(userId);
        return to;
    }

    public PostCommentTimelineTO withCreatedAt() {
        this.setCreatedAt(now());
        return this;
    }

    public PostCommentTimelineTO withUpdatedAt() {
        this.setUpdatedAt(now());
        return this;
    }
}
