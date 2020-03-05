package com.fyself.post.service.post.datasource.domain;


import com.fyself.post.service.post.datasource.domain.subentities.Content;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "post")
public class Post extends DomainAuditEntity {

    private static final long serialVersionUID = 8909993127910604604L;
    private Set<Content> contents;

    public Set<Content> getContents() {
        return contents;
    }

    public void setContents(Set<Content> contents) {
        this.contents = contents;
    }
}
