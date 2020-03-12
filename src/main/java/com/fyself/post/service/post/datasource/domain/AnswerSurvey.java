package com.fyself.post.service.post.datasource.domain;


import com.fyself.post.service.post.datasource.domain.subentities.Answer;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import com.fyself.seedwork.service.repository.mongodb.stereotype.CascadeReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "answer_survey")
public class AnswerSurvey extends DomainAuditEntity {

    private static final long serialVersionUID = -5823858860860663049L;
    @DBRef
    @CascadeReference
    private Post post;
    private Answer answer;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
