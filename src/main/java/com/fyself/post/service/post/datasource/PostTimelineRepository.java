package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.PostTimeline;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;

/**
 * {@link PostTimeline}'s Mongo repository.
 *
 * @author jmmarin
 * @since 0.0.1
 */
public interface PostTimelineRepository extends MongoRepository<PostTimeline> {

    @Override
    default Class<PostTimeline> getEntityClass() {
        return PostTimeline.class;
    }

    @Query(value = "{ 'deleted' : {$ne : true}, 'user': ?0}", sort = "{ 'createdAt': -1}")
    Flux<PostTimeline> findAllByUser(String userId);
}
