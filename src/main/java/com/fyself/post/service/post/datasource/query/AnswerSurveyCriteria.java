package com.fyself.post.service.post.datasource.query;

import com.fyself.post.service.post.contract.to.criteria.enums.TypeSearch;
import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;
import com.fyself.seedwork.service.repository.mongodb.criteria.DomainCriteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.List;
import java.util.Set;

import static com.fyself.seedwork.service.repository.mongodb.criteria.Criterion.and;
import static org.springframework.data.mongodb.core.query.Criteria.where;


public class AnswerSurveyCriteria extends DomainCriteria<AnswerSurvey> {
    private static final long serialVersionUID = -1173576727652462350L;

    private Post post;
    private TypeSurvey typeSurvey;
    private String user;
    private String owner;
    private List<String> postIds;
    private TypeSearch typeSearch;

    public AnswerSurveyCriteria() {
        super(AnswerSurvey.class);
    }

    @Override
    protected Criteria force() {
        return null;
    }

    @Override
    public Set<String> searchField() {
        return null;
    }

    @Override
    public CriteriaDefinition getPredicate() {
        if (!this.getPostIds().isEmpty() && this.getPost() == null) {
            return and(this.matchAnswer(), this.matchPostId());
        }

        if (this.matchPost() != null) {
            return and(this.matchPost(), this.matchOwner());
        }

        return and(this.matchPost(), this.matchAnswer(), this.matchOwner());
    }

    private Criteria matchPost() {
        return this.post != null ? where("post.id").is(this.getPost().getId()) : null;
    }

    private Criteria matchAnswer() {
        return this.typeSurvey != null ? where("answer.type").is(this.getTypeSurvey()) : null;
    }

    private Criteria matchOwner() {
        return this.owner != null ? where("owner").is(this.getOwner()) : null;
    }

    private Criteria matchPostId() {
        return this.getPostIds() != null && !this.getPostIds().isEmpty() ? where("post.id").in(this.getPostIds()) : null;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

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

    public List<String> getPostIds() {
        return postIds;
    }

    public void setPostIds(List<String> postIds) {
        this.postIds = postIds;
    }

    public TypeSearch getTypeSearch() {
        return typeSearch;
    }

    public void setTypeSearch(TypeSearch typeSearch) {
        this.typeSearch = typeSearch;
    }
}
