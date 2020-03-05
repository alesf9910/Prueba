package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Map;

public class SurveyContent extends Content{

    private String question;
    private Map<String,Object> options;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Object> options) {
        this.options = options;
    }
}
