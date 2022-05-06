package com.fyself.post.service.post.datasource.query;

import com.fyself.post.service.post.contract.to.criteria.enums.TypeSearch;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.repository.mongodb.criteria.DomainCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Set;

import static com.fyself.seedwork.service.repository.mongodb.criteria.Criterion.and;
import static org.springframework.data.mongodb.core.query.Criteria.where;

public class PostSearchByEnterpriseCriteria extends DomainCriteria<Post> {
    private static final long serialVersionUID = -3800122150368324780L;

    private Access access;
    private String owner;
    private boolean active;
    private boolean blocked;
    private TypeSearch type;
    private String enterprise;
    private boolean workspace;

    public PostSearchByEnterpriseCriteria() {
        super(Post.class);
    }

    @Override
    protected Criteria force() {
        if(type==null){
            return and(matchAccess(), matchActive(), matchBlocked(), matchOwner(), matchEnterprise(), notDeleted());
        } else {
            switch (type) {
                case SHARED:
                    return and(matchAccess(), matchActive(), matchBlocked(), matchShared(), matchEnterprise(), notDeleted());
                case ME:
                case ALL:
                default:
                    return and(matchAccess(), matchActive(), matchBlocked(), matchOwner(), matchEnterprise(), notDeleted());
            }
        }
    }

    @Override
    public Set<String> searchField() {return null;}

    private Criteria matchShared() {
        return this.owner != null ? where("sharedWith").all(this.getOwner()) : null;
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
    private Criteria matchOwner() {
        return this.owner != null ? where("owner").is(this.getOwner()) : null;
    }
    private Criteria matchEnterprise() {
        if(this.workspace==true)
        {
            return and(where("workspace").is(this.workspace),
                    this.enterprise != null ? where("enterprise").is(this.getEnterprise()) : null,
                    where("sharedWith").exists(false));
        }
        else
            return null;
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

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public TypeSearch getType() {
        return type;
    }
    public void setType(TypeSearch type) {
        this.type = type;
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

    @Override
    public Sort getSort() {
        return Sort.by(Sort.Order.desc("createdAt"));
    }
}
