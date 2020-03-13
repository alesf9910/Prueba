package com.fyself.post.service.post.datasource.query;

import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.subentities.Answer;
import com.fyself.seedwork.service.repository.mongodb.criteria.DomainCriteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import static com.fyself.seedwork.service.repository.mongodb.criteria.Criterion.and;
import static org.springframework.data.mongodb.core.query.Criteria.where;


public class AnswerSurveyCriteria extends DomainCriteria<AnswerSurvey> {
    private static final long serialVersionUID = -1173576727652462350L;

    private Post post;
    private Answer answer;
    private String user;
    private String owner;

    public AnswerSurveyCriteria() {
        super(AnswerSurvey.class);
    }

    @Override
    public CriteriaDefinition getPredicate() {
        return and(this.matchPost(), this.matchAnswer(), this.matchOwner(), this.matchUser());
    }

    private Criteria matchPost() {
        return this.answer != null ? where("post.id").is(this.getPost()) : null;
    }

    private Criteria matchAnswer() {
        return this.answer != null ? where("answer.id").is(this.getAnswer()) : null;
    }

    private Criteria matchOwner() {
        return this.owner != null ? where("owner").is(this.getOwner()) : null;
    }

    private Criteria matchUser() {
        return this.user != null ? where("user").is(this.getUser()) : null;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
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

}
