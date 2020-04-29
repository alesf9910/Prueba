package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.Comment;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;

/**
 * {@link Comment}'s Mongo repository.
 *
 * @author jmmarin
 * @since 0.0.1
 */
public interface CommentRepository extends MongoRepository<Comment> {

    @Override
    default Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
