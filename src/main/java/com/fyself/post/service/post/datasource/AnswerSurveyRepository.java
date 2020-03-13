package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.seedwork.service.repository.mongodb.MongoRepository;

public interface AnswerSurveyRepository extends MongoRepository<AnswerSurvey> {
    @Override
    default Class<AnswerSurvey> getEntityClass() {
        return AnswerSurvey.class;
    }
}
