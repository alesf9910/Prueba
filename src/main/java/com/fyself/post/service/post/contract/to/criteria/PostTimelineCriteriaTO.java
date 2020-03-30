package com.fyself.post.service.post.contract.to.criteria;

import com.fyself.seedwork.service.to.CriteriaTO;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import javax.validation.constraints.NotNull;

public class PostTimelineCriteriaTO extends CriteriaTO {
    private static final long serialVersionUID = -3722987217715369547L;

    private String user;
    private boolean me;

    @ReadOnly
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @NotNull
    public boolean isMe() {
        return me;
    }

    public void setMe(boolean me) {
        this.me = me;
    }

    public PostTimelineCriteriaTO withUser(String user) {
        this.setUser(user);
        return this;
    }
}
