package com.fyself.post.service.post.datasource.query;

import com.fyself.post.service.post.datasource.domain.PostTimeline;
import com.fyself.seedwork.service.repository.mongodb.criteria.DomainCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import java.util.Set;

import static com.fyself.seedwork.service.repository.mongodb.criteria.Criterion.and;
import static org.springframework.data.mongodb.core.query.Criteria.where;


public class PostTimelineCriteria extends DomainCriteria<PostTimeline> {
    private static final long serialVersionUID = -5368483043147402013L;

    private String user;
    private String enterprise;
    private boolean workspace;

    public PostTimelineCriteria() {
        super(PostTimeline.class);
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
        return and(matchUser(),matchEnterprise());
    }

    private Criteria matchUser() {
        return this.user != null ? where("user").is(this.getUser()) : null;
    }

    private Criteria matchEnterprise() {
        if(this.workspace)
        {
            return and(where("workspace").is(this.workspace),this.enterprise != null ? where("enterprise").is(this.getEnterprise()) : null);
        }
        else
            return null;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public Sort getSort() {
        return Sort.by(Sort.Order.desc("createdAt"));
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public boolean isWorkspace() {
        return workspace;
    }

    public void setWorkspace(boolean workspace) {
        this.workspace = workspace;
    }
}
