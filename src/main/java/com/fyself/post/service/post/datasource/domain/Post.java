package com.fyself.post.service.post.datasource.domain;


import com.fyself.post.service.post.datasource.domain.subentities.Content;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "post")
public class Post extends DomainAuditEntity {

    private static final long serialVersionUID = 8909993127910604604L;
    private Content content;
    private Access access;
    private boolean active;
    private boolean blocked;
    private String urlImage;
    private Set<String> sharedWith;

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Set<String> getSharedWith() {
        return sharedWith;
    }

    public void setSharedWith(Set<String> sharedWith) {
        this.sharedWith = sharedWith;
    }

    public Post shareUser(String user) {
        if (this.sharedWith == null) {
            this.sharedWith = Set.of(user);
        } else {
            sharedWith.add(user);
        }
        return this;
    }

    public Post shareBulk(Set<String> users) {
        this.setSharedWith(users);
        return this;
    }

    public Post stopShareUser(String user) {
        if (this.sharedWith != null) {
            sharedWith.remove(user);
        }
        return this;
    }

    public Post withContent(Content content) {
        this.setContent(content);
        return this;
    }
}
