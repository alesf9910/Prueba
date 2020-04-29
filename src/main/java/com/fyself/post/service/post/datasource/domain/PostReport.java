package com.fyself.post.service.post.datasource.domain;

import com.fyself.post.service.post.datasource.domain.enums.ReportingReason;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import com.fyself.seedwork.service.repository.mongodb.stereotype.CascadeReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "post_report")
public class PostReport extends DomainAuditEntity {
    private static final long serialVersionUID = 8909993127910604604L;

    @DBRef
    @CascadeReference
    private Post post;
    private String user;
    private String description;
    private ReportingReason reason;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportingReason getReason() {
        return reason;
    }

    public void setReason(ReportingReason reason) {
        this.reason = reason;
    }

}
