package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Map;

public class AnswerHierarchy extends Answer {

    private Map<String, Integer> answers;

    public Map<String, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Integer> answers) {
        this.answers = answers;
    }
}
