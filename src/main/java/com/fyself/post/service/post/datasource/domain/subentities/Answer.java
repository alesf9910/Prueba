package com.fyself.post.service.post.datasource.domain.subentities;


import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;

public abstract class Answer {

    private TypeSurvey type;

    public TypeSurvey getType() {
        return type;
    }

    public void setType(TypeSurvey type) {
        this.type = type;
    }
}
