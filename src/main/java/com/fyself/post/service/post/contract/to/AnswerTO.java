package com.fyself.post.service.post.contract.to;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;

import java.io.Serializable;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AnswerAskTO.class, name = "SURVEY_ASK"),
        @JsonSubTypes.Type(value = AnswerChoiceTO.class, name = "SURVEY_CHOICE"),
        @JsonSubTypes.Type(value = AnswerHierarchyTO.class, name = "SURVEY_HIERARCHY"),
        @JsonSubTypes.Type(value = AnswerRateTO.class, name = "SURVEY_RATE")
})
public abstract class AnswerTO implements Serializable {
    private static final long serialVersionUID = 9043563622743672733L;
    private TypeSurvey type;

    public TypeSurvey getType() {
        return type;
    }

    public void setType(TypeSurvey type) {
        this.type = type;
    }
}
