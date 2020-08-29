package com.fyself.post.service.post.contract.to;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;

import java.io.Serializable;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        visible = true,
        property = "typeContent")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ProfileContentTO.class, name = "PROFILE"),
        @JsonSubTypes.Type(value = AwardContentTO.class, name = "AWARD"),
        @JsonSubTypes.Type(value = TextContentTO.class, name = "TEXT"),
        @JsonSubTypes.Type(value = SurveyContentTO.class, name = "SURVEY_ASK"),
        @JsonSubTypes.Type(value = ChoiceSurveyTO.class, name = "SURVEY_CHOICE"),
        @JsonSubTypes.Type(value = HierarchySurveyTO.class, name = "SURVEY_HIERARCHY"),
        @JsonSubTypes.Type(value = RateSurveyTO.class, name = "SURVEY_RATE"),
        @JsonSubTypes.Type(value = SharedPostTO.class, name = "SHARED_POST")
})
public abstract class ContentTO implements Serializable {

    private static final long serialVersionUID = -7470259196251301570L;
    private TypeContent typeContent;

    public TypeContent getTypeContent() {
        return typeContent;
    }

    public void setTypeContent(TypeContent typeContent) {
        this.typeContent = typeContent;
    }
}
