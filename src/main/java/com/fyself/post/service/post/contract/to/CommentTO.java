package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class CommentTO extends DomainAuditTransferObject {
    private static final long serialVersionUID = -853413377748237564L;

    private String post;
    private String content;
    private String url;
    private String father;
    private PagedList<CommentTO> childrens;
    private boolean workspace;
    private String enterprise;

    //@NotBlank
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    @ReadOnly
    public PagedList<CommentTO> getChildrens() {
        return childrens;
    }

    public void setChildrens(PagedList<CommentTO> childrens) {
        this.childrens = childrens;
    }

    @NotNull
    @ReadOnly
    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    @Override
    @ReadOnly
    public String getOwner() {
        return super.getOwner();
    }

    @Override
    @ReadOnly
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    @ReadOnly
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    @ReadOnly
    public String getId() {
        return super.getId();
    }

    public CommentTO withId(String id) {
        this.setId(id);
        return this;
    }

    public CommentTO withPost(String post) {
        setPost(post);
        return this;
    }

    public CommentTO withUserId(String id) {
        this.setOwner(id);
        return this;
    }

    public CommentTO withCreatedAt() {
        this.setCreatedAt(now());
        return this;
    }

    public CommentTO withUpdatedAt() {
        this.setUpdatedAt(now());
        return this;
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

    public CommentTO putEnterprise(String enterprise) {
        this.enterprise = enterprise;
        return this;
    }

    public CommentTO putWorkspace(boolean workspace) {
        this.workspace = workspace;
        return this;
    }
}
