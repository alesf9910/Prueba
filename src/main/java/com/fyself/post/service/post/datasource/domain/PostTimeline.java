package com.fyself.post.service.post.datasource.domain;


import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import com.fyself.seedwork.service.repository.mongodb.stereotype.CascadeReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "post-timeline")
public class PostTimeline extends DomainAuditEntity {

    private static final long serialVersionUID = -1974933551312804543L;
    @DBRef
    @CascadeReference
    private Post post;
    private String user;
    private boolean workspace;
    private String enterprise;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public boolean isWorkspace() {
        return workspace;
    }

    public void setWorkspace(boolean workspace) {
        this.workspace = workspace;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
