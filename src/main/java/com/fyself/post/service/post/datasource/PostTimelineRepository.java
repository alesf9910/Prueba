package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.PostTimeline;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;

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

}
