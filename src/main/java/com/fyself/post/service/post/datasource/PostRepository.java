package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

/**
 * {@link Post}'s Mongo repository.
 *
 * @author jmmarin
 * @since 0.0.1
 */
public interface PostRepository extends MongoRepository<Post> {
    @Query(value = "{ 'deleted' : {$ne : true}, 'pinned': {$ne: true}, 'owner': ?0}")
    Flux<Post> findAllByOwnerPinned(String owner);
}
