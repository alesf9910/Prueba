package com.fyself.post.service.post.datasource.domain;


import com.fyself.post.service.post.datasource.domain.enums.ReactionType;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainAuditEntity;
import com.fyself.seedwork.service.repository.mongodb.stereotype.CascadeReference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "post_reaction")
public class Reaction extends DomainAuditEntity {

    private static final long serialVersionUID = 2392223630818952482L;

    @DBRef
    @CascadeReference
    private Post post;
    private ReactionType reaction;

    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }

    public ReactionType getReaction() {
        return reaction;
    }
    public void setReaction(ReactionType reaction) {
        this.reaction = reaction;
    }

}
