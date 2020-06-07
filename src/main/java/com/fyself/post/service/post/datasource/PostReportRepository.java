package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.PostReport;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Mono;

public interface PostReportRepository extends MongoRepository<PostReport> {


    @Query(value = "{ 'deleted' : {$ne : true}, 'post.id': ?0}", count = true)
    Mono<Long> countAllByPost(String post);
}
