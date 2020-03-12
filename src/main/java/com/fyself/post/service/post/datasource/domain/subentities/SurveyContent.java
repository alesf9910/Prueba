package com.fyself.post.service.post.datasource.domain.subentities;


import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;

public abstract class SurveyContent extends Content {

    private TypeSurvey type;
    private String ask;

    public TypeSurvey getType() {
        return type;
    }

    public void setType(TypeSurvey type) {
        this.type = type;
    }

    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }
}
