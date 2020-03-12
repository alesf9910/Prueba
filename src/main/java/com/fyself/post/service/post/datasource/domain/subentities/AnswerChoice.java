package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Map;

public class AnswerChoice extends Answer {

    private Map<String, Boolean> answers;

    public Map<String, Boolean> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Boolean> answers) {
        this.answers = answers;
    }
}
