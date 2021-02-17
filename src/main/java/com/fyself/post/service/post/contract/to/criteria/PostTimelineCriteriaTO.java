package com.fyself.post.service.post.contract.to.criteria;

import com.fyself.post.service.post.contract.to.criteria.enums.TypeSearch;
import com.fyself.seedwork.service.to.CriteriaTO;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import javax.validation.constraints.NotNull;

public class PostTimelineCriteriaTO extends CriteriaTO {
    private static final long serialVersionUID = -3722987217715369547L;

    private String user;
    private String enterprise;
    private TypeSearch type;
    private boolean workspace;

    @ReadOnly
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    @NotNull
    public TypeSearch getType() {
        return type;
    }
    public void setType(TypeSearch type) {
        this.type = type;
    }

    public PostTimelineCriteriaTO withUser(String user) {
        this.setUser(user);
        return this;
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
