package com.fyself.post.service.post.datasource.domain;


import com.fyself.post.service.post.datasource.domain.subentities.Content;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "post")
public class Post extends DomainAuditEntity {

    private static final long serialVersionUID = 8909993127910604604L;
    private Content content;
    private Access access;
    private boolean status;
    private boolean blocked;


    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
