package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.PostCommentTimeline;
import com.fyself.post.service.post.datasource.domain.PostTimeline;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * {@link PostTimeline}'s Mongo repository.
 *
 * @author jmmarin
 * @since 0.0.1
 */
public interface PostCommentTimelineRepository extends MongoRepository<PostCommentTimeline> {


    /*@Query(value = "{ 'deleted' : {$ne : true}, 'user': ?0}", sort = "{ 'createdAt': -1}")
    Flux<PostTimeline> findAllByUser(String userId);

    Mono<Void> deleteAllByPost_IdAndUser(String post_id, String user);

    Mono<Boolean> existsByPost_IdAndOwner(String post_id, String owner);*/
}