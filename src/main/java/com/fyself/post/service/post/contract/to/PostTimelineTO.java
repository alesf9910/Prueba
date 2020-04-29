package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class PostTimelineTO extends DomainAuditTransferObject {

    private static final long serialVersionUID = -8526218291708837581L;

    private String post;
    private String user;

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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

    public static PostTimelineTO from(String userId, String post, String owner) {
        var to = new PostTimelineTO();
        to.setOwner(owner);
        to.setCreatedAt(now());
        to.setUpdatedAt(now());
        to.setPost(post);
        to.setUser(userId);
        return to;
    }

    public PostTimelineTO withCreatedAt() {
        this.setCreatedAt(now());
        return this;
    }

    public PostTimelineTO withUpdatedAt() {
        this.setUpdatedAt(now());
        return this;
    }
}
