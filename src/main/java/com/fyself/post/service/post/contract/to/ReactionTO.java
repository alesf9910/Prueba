package com.fyself.post.service.post.contract.to;

import com.fyself.post.service.post.datasource.domain.enums.ReactionType;
import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

/**
 * Controller for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
public class ReactionTO extends DomainAuditTransferObject {

    private String post;
    private ReactionType reaction;
    private boolean workspace;
    private String enterprise;

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

    @NotNull
    @NotBlank
    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }

    @NotNull
    public ReactionType getReaction() {
        return reaction;
    }
    public void setReaction(ReactionType reaction) {
        this.reaction = reaction;
    }

    public ReactionTO withOwner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public ReactionTO withPost(String post) {
        this.setPost(post);
        return this;
    }

    public ReactionTO withReportId(String reportId) {
        this.setId(reportId);
        return this;
    }

    public ReactionTO withCreateAt() {
        this.setCreatedAt(now());
        return this;
    }

    public ReactionTO withUpdateAt() {
        this.setUpdatedAt(now());
        return this;
    }

    public boolean isWorkspace() {
        return workspace;
    }

    public void setWorkspace(boolean workspace) {
        this.workspace = workspace;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public ReactionTO putEnterprise(String enterprise) {
        this.enterprise = enterprise;
        return this;
    }
}
