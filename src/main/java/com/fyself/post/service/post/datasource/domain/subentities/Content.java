package com.fyself.post.service.post.datasource.domain.subentities;


import com.fyself.post.service.post.datasource.domain.enums.TypeContent;

public abstract class Content {

    private TypeContent typeContent;

    public TypeContent getTypeContent() {
        return typeContent;
    }

    public void setTypeContent(TypeContent typeContent) {
        this.typeContent = typeContent;
    }
}
