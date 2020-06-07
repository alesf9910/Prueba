package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;

/**
 * {@link Post}'s Mongo repository.
 *
 * @author jmmarin
 * @since 0.0.1
 */
public interface PostRepository extends MongoRepository<Post> {


}
