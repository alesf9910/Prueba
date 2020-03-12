package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.PostReport;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;

public interface PostReportRepository extends MongoRepository<PostReport> {

    @Override
    default Class<PostReport> getEntityClass() {
        return PostReport.class;
    }
}
