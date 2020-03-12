package com.fyself.post.service.post.contract.to;


import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

import static java.time.LocalDateTime.now;

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

    @NotEmpty
    public Set<ContentTO> getContents() {
        return contents;
    }

    public void setContents(Set<ContentTO> contents) {
        this.contents = contents;
    }

    public PostTO withUserId(String id) {
        this.setOwner(id);
        return this;
    }

    public PostTO withCreatedAt() {
        this.setCreatedAt(now());
        return this;
    }

    public PostTO withUpdatedAt() {
        this.setUpdatedAt(now());
        return this;
    }
}
