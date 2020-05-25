package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import reactor.core.publisher.Mono;

public interface AnswerSurveyRepository extends MongoRepository<AnswerSurvey> {

    @Query(value = "{ 'deleted' : {$ne : true}, 'post': ?0, 'owner': ?1}")
    Mono<AnswerSurvey> findByPostAndUser(String post, String user);
}
