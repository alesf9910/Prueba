package com.fyself.post.service.post.contract.to.criteria;

import com.fyself.seedwork.service.to.CriteriaTO;

public class PostReportCriteriaTO extends CriteriaTO {
    private static final long serialVersionUID = 2138765270323056960L;

    private String post;
    private String user;
    private String owner;

    public PostReportCriteriaTO(String post, String user, String owner) {
        this.post = post;
        this.user = user;
        this.owner = owner;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public PostReportCriteriaTO withOwner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public PostReportCriteriaTO withUser(String user) {
        this.setUser(user);
        return this;
    }

    public PostReportCriteriaTO withPost(String post) {
        this.setPost(post);
        return this;
    }

}
