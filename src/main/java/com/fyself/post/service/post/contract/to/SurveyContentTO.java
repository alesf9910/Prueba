package com.fyself.post.service.post.contract.to;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SurveyContentTO.class, name = "ASK"),
        @JsonSubTypes.Type(value = ChoiceSurveyTO.class, name = "CHOICE"),
        @JsonSubTypes.Type(value = HierarchySurveyTO.class, name = "HIERARCHY"),
        @JsonSubTypes.Type(value = RateSurveyTO.class, name = "RATE")
})
public class SurveyContentTO extends ContentTO{

    private static final long serialVersionUID = 2597014116630110418L;
    private TypeSurvey type;
    private String ask;

    @NotNull
    public TypeSurvey getType() {
        return type;
    }

    public void setType(TypeSurvey type) {
        this.type = type;
    }

    @NotBlank
    public String getAsk() {
        return ask;
    }

    public void setAsk(String ask) {
        this.ask = ask;
    }
}
