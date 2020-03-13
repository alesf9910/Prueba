package com.fyself.post.service.post.contract.to.criteria;

import com.fyself.post.service.post.contract.to.AnswerTO;
import com.fyself.seedwork.service.to.CriteriaTO;

public class AnswerSurveyCriteriaTO extends CriteriaTO {
    private static final long serialVersionUID = 2138765270323056960L;

    private String post;
    private AnswerTO answer;
    private String user;
    private String owner;

    public AnswerSurveyCriteriaTO(AnswerTO answer, String user, String owner, String post) {
        this.answer = answer;
        this.user = user;
        this.owner = owner;
        this.post = post;
    }

    public AnswerTO getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerTO answer) {
        this.answer = answer;
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

    public AnswerSurveyCriteriaTO withAnswer(AnswerTO answer) {
        this.setAnswer(answer);
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
}
