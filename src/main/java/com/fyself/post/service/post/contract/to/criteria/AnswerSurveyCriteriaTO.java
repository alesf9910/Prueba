package com.fyself.post.service.post.contract.to.criteria;

import com.fyself.post.service.post.contract.to.criteria.enums.TypeSearch;
import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;
import com.fyself.seedwork.service.to.CriteriaTO;

import java.util.List;

public class AnswerSurveyCriteriaTO extends CriteriaTO {
    private static final long serialVersionUID = 2138765270323056960L;

    private String post;
    private TypeSurvey typeSurvey;
    private String user;
    private String owner;
    private List<String> postIds;
    private TypeSearch typeSearch;

    public TypeSurvey getTypeSurvey() {
        return typeSurvey;
    }

    public void setTypeSurvey(TypeSurvey typeSurvey) {
        this.typeSurvey = typeSurvey;
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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<String> postIds) {
        this.postIds = postIds;
    }

    public AnswerSurveyCriteriaTO withAnswer(TypeSurvey answer) {
        this.setTypeSurvey(answer);
        return this;
    }

    public AnswerSurveyCriteriaTO withPost(String post) {
        this.setPost(post);
        return this;
    }

    public AnswerSurveyCriteriaTO withOwner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public AnswerSurveyCriteriaTO withUser(String user) {
        this.setUser(user);
        return this;
    }

    public AnswerSurveyCriteriaTO withPostIds(List<String> postIds) {
        setPostIds(postIds);
        return this;
    }

    public TypeSearch getTypeSearch() {
        return typeSearch;
    }

    public void setTypeSearch(TypeSearch typeSearch) {
        this.typeSearch = typeSearch;
    }
}
