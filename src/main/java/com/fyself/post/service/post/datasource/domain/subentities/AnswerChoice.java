package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Set;

public class AnswerChoice extends Answer {

    private Set<String> answers;

    public Set<String> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<String> answers) {
        this.answers = answers;
    }
}
