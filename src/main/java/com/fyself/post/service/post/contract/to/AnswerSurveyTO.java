package com.fyself.post.service.post.contract.to;

import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;
import org.apache.commons.codec.digest.DigestUtils;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public class AnswerSurveyTO extends DomainAuditTransferObject {
    private static final long serialVersionUID = -4344616978263673051L;

    private String post;
    private AnswerTO answer;

    public AnswerSurveyTO(String post, AnswerTO answer) {
        this.post = post;
        this.answer = answer;
    }

    public AnswerSurveyTO() {

    }

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

    public AnswerTO getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerTO answer) {
        this.answer = answer;
    }

    public AnswerSurveyTO withId(String id) {
        String answer = DigestUtils.md5Hex(String.format("%s%s", id, this.getOwner()));
        this.setId(answer);
        return this;
    }

    public AnswerSurveyTO withOwner(String owner) {
        this.setOwner(owner);
        return this;
    }

    public AnswerSurveyTO withReportId(String reportId) {
        this.setId(reportId);
        return this;
    }

    public AnswerSurveyTO withCreateAt() {
        this.setCreatedAt(now());
        return this;
    }

    public AnswerSurveyTO withCreateAt(LocalDateTime time) {
        this.setCreatedAt(time);
        return this;
    }

    public AnswerSurveyTO withUpdateAt(LocalDateTime time) {
        this.setUpdatedAt(time);
        return this;
    }

    public AnswerSurveyTO withUpdateAt() {
        this.setUpdatedAt(now());
        return this;
    }
}
