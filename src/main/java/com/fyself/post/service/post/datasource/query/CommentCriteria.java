package com.fyself.post.service.post.datasource.query;

import com.fyself.post.service.post.datasource.domain.Comment;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.seedwork.service.repository.mongodb.criteria.DomainCriteria;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;

import static com.fyself.seedwork.service.repository.mongodb.criteria.Criterion.and;
import static org.springframework.data.mongodb.core.query.Criteria.where;


public class CommentCriteria extends DomainCriteria<Comment> {
    private static final long serialVersionUID = -1173576727652462350L;

    private Post post;

    public CommentCriteria() {
        super(Comment.class);
    }

    @Override
    public CriteriaDefinition getPredicate() {
        return and(matchPost());
    }

    private Criteria matchPost() {
        return this.post != null ? where("post").is(this.getPost()) : null;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
