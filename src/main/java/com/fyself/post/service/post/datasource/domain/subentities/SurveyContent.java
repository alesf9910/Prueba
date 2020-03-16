package com.fyself.post.service.post.datasource.domain.subentities;


import java.util.Map;

public class SurveyContent extends Content {

    private Map<String,String> ask;

    public Map<String,String> getAsk() {
        return ask;
    }

    public void setAsk(Map<String,String> ask) {
        this.ask = ask;
    }
}
