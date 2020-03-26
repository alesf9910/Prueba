package com.fyself.post.service.post.contract.to.criteria;

import com.fyself.seedwork.service.to.CriteriaTO;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

public class PostTimelineCriteriaTO extends CriteriaTO {
    private static final long serialVersionUID = -3722987217715369547L;

    private String user;

    @ReadOnly
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public PostTimelineCriteriaTO withUser(String user) {
        this.setUser(user);
        return this;
    }
}
