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
        @JsonSubTypes.Type(value = ImageContentTO.class, name = "IMAGE"),
        @JsonSubTypes.Type(value = LinkContentTO.class, name = "LINK"),
        @JsonSubTypes.Type(value = SurveyContentTO.class, name = "SURVEY"),
        @JsonSubTypes.Type(value = TextContentTO.class, name = "TEXT")
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
