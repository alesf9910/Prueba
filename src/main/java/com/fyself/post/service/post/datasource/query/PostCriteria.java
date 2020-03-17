package com.fyself.post.service.post.datasource.query;

import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.repository.mongodb.criteria.DomainCriteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import static com.fyself.seedwork.service.repository.mongodb.criteria.Criterion.and;
import static org.springframework.data.mongodb.core.query.Criteria.where;


public class PostCriteria extends DomainCriteria<Post> {
    private static final long serialVersionUID = -3800122150368324780L;

    private Access access;
    private boolean active;
    private boolean blocked;

    public PostCriteria() {
        super(Post.class);
    }

    @Override
    public CriteriaDefinition getPredicate() {
        return and(matchAccess(), matchActive(), matchBlocked());
    }

    private Criteria matchAccess() {
        return this.access != null ? where("access").is(this.getAccess()) : null;
    }

    private Criteria matchActive() {
        return this.active ? where("active").is(this.isActive()) : null;
    }

    private Criteria matchBlocked() {
        return this.blocked ? where("blocked").is(this.isBlocked()) : null;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
