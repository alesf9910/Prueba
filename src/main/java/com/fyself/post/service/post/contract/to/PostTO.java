package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import java.time.LocalDateTime;
import java.util.Set;

public class PostTO extends DomainAuditTransferObject {

    private static final long serialVersionUID = -1060990002867022621L;
    private Set<ContentTO> contents;

    @Override
    @ReadOnly
    public String getOwner() {
        return super.getOwner();
    }

    @Override
    @ReadOnly
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    @ReadOnly
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    @ReadOnly
    public String getId() {
        return super.getId();
    }

    public Set<ContentTO> getContents() {
        return contents;
    }

    public void setContents(Set<ContentTO> contents) {
        this.contents = contents;
    }

    public PostTO withOwner(String owner) {
        this.setOwner(owner);
        return this;
    }
}
