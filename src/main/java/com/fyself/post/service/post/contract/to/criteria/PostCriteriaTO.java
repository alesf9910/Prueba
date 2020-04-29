package com.fyself.post.service.post.contract.to.criteria;

import com.fyself.post.service.post.contract.to.criteria.enums.TypeSearch;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.to.CriteriaTO;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

public class PostCriteriaTO extends CriteriaTO {
    private static final long serialVersionUID = -4573953476430812827L;

    private Access access;
    private String owner;
    private boolean active;
    private boolean blocked;
    private TypeSearch type;

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

    public TypeSearch getType() {
        return type;
    }
    public void setType(TypeSearch type) {
        this.type = type;
    }

    @ReadOnly
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public PostCriteriaTO withOwner(String owner) {
        this.setOwner(owner);
        return this;
    }
}
