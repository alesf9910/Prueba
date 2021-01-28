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
    private boolean pinned;
    private TypeSearch type;
    private String enterprise;
    private boolean workspace;

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

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
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
