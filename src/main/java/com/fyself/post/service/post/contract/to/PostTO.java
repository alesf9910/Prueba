package com.fyself.post.service.post.contract.to;


import com.fyself.post.service.post.datasource.domain.enums.ReactionType;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.to.DomainAuditTransferObject;
import com.fyself.seedwork.service.to.annotation.ReadOnly;

import java.util.HashMap;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import static java.time.LocalDateTime.now;

public class PostTO extends DomainAuditTransferObject {

    private static final long serialVersionUID = -1060990002867022621L;
    private ContentTO content;
    private Access access;
    private boolean active;
    private boolean blocked;
    private String urlImage;
    private Set<String> sharedWith;
    private boolean pinned;
    private Long comments;
    private ReactionType reaction;
    private Map<ReactionType,Long>  reactionStats= new HashMap<>();
    private boolean isWorkspace;
    private String enterprise;

    @ReadOnly
    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }
    public PostTO putCount(Long comments) {
        this.comments = comments;
        return this;
    }

    @Override
    @ReadOnly
    public String getOwner() {
        return super.getOwner();
    }

    @Override
    @ReadOnly
    public LocalDateTime getCreatedAt() {
        return super.getCreatedAt();
    }

    @Override
    @ReadOnly
    public LocalDateTime getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    @ReadOnly
    public String getId() {
        return super.getId();
    }

    @NotNull
    @Valid
    public ContentTO getContent() {
        return content;
    }

    public void setContent(ContentTO content) {
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

    public PostTO withId(String id) {
        this.setId(id);
        return this;
    }

    public PostTO withUserId(String id) {
        this.setOwner(id);
        return this;
    }

    public PostTO withCreatedAt() {
        this.setCreatedAt(now());
        return this;
    }

    public PostTO withUpdatedAt() {
        this.setUpdatedAt(now());
        return this;
    }

    @ReadOnly
    public Set<String> getSharedWith() {
        return sharedWith;
    }
    public void setSharedWith(Set<String> sharedWith) {
        this.sharedWith = sharedWith;
    }

    public ReactionType getReaction() {
        return reaction;
    }

    public void setReaction(ReactionType reaction) {
        this.reaction = reaction;
    }

    public Map<ReactionType, Long> getReactionStats() {
        return reactionStats;
    }

    public void setReactionStats(Map<ReactionType, Long> reactionStats) {
        this.reactionStats = reactionStats;
    }

    public PostTO putReaction(ReactionType reactionType) {
        this.setReaction(reactionType);
        return this;
    }

    public PostTO putReactionStats(Map map) {
        this.setReactionStats(map);
        return this;
    }

    public PostTO withSharedContent(PostTO father){
        ((SharedPostTO)this.getContent()).setPostTo(father);
        return this;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public boolean isWorkspace() {
        return isWorkspace;
    }

    public void setWorkspace(boolean workspace) {
        isWorkspace = workspace;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
