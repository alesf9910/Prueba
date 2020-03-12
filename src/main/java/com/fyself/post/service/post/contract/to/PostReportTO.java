package com.fyself.post.service.post.contract.to;

import com.fyself.post.service.post.datasource.domain.enums.ReportingReason;
import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class PostReportTO extends DomainAuditTransferObject {
    private String post;
    private String user;
    private String description;
    private ReportingReason reason;

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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportingReason getReason() {
        return reason;
    }

    public void setReason(ReportingReason reason) {
        this.reason = reason;
    }

    public PostReportTO withOwner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public PostReportTO withPost(String post) {
        this.setPost(post);
        return this;
    }

    public PostReportTO withUser(String user) {
        this.setUser(user);
        return this;
    }

    public PostReportTO withId(String id) {
        String reportId = DigestUtils.md5DigestAsHex(String.format("%s%s", this.getUser(), id).getBytes());
        this.setId(reportId);
        return this;
    }

    public PostReportTO withReportId(String reportId) {
        this.setId(reportId);
        return this;
    }

    public PostReportTO withCreateAt() {
        this.setCreatedAt(now());
        return this;
    }

    public PostReportTO withUpdateAt() {
        this.setUpdatedAt(now());
        return this;
    }
}
