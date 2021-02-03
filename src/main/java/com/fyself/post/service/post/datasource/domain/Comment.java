package com.fyself.post.service.post.datasource.domain;


import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import com.fyself.seedwork.service.repository.mongodb.stereotype.CascadeReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comment")
public class Comment extends DomainAuditEntity {

    private static final long serialVersionUID = 2392223630818952482L;
    @DBRef
    @CascadeReference
    private Post post;
    private String content;
    private String url;
    private boolean workspace;
    private String enterprise;
    @DBRef
    @CascadeReference
    private Comment father;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Comment getFather() {
        return father;
    }

    public void setFather(Comment father) {
        this.father = father;
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
