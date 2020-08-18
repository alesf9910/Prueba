package com.fyself.post.service.post.datasource.query;

import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.PostReport;
import com.fyself.seedwork.service.repository.mongodb.criteria.DomainCriteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.Set;

import static com.fyself.seedwork.service.repository.mongodb.criteria.Criterion.and;
import static org.springframework.data.mongodb.core.query.Criteria.where;


public class PostReportCriteria extends DomainCriteria<PostReport> {
    private static final long serialVersionUID = -1173576727652462350L;

    private Post post;
    private String user;
    private String owner;

    public PostReportCriteria() {
        super(PostReport.class);
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
        return and(this.matchPost(), this.matchOwner(), this.matchUser());
    }

    private Criteria matchPost() {
        return this.post != null ? where("post").is(this.getPost()) : null;
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
