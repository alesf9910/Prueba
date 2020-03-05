package com.fyself.post.service.post.contract.to;


import java.util.Map;

public class SurveyContentTO extends ContentTO{

    private static final long serialVersionUID = 2597014116630110418L;
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
