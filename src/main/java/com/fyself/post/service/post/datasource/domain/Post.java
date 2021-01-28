package com.fyself.post.service.post.datasource.domain;


import com.fyself.post.service.post.datasource.domain.subentities.Content;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;
import reactor.core.publisher.Mono;

@Document(collection = "post")
public class Post extends DomainAuditEntity {

    private static final long serialVersionUID = 8909993127910604604L;
    private Content content;
    private Access access;
    private boolean active;
    private boolean blocked;
    private String urlImage;
    private Set<String> sharedWith;
    private boolean pinned;
    private boolean workspace;
    private String enterprise;

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
        this.setUpdatedAt(LocalDateTime.now());
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

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public Post putPinned(Boolean v) {
        setPinned(v);
        return this;
    }

    public boolean getWorkspace() {
        return workspace;
    }

    public void setWorkspace(boolean workspace) {
        this.workspace = workspace;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
