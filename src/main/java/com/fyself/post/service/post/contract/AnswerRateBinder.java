package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.subentities.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


/**
 * Interface binder for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Mapper
public interface AnswerRateBinder {
    AnswerRateBinder ANSWER_RATE_BINDER = Mappers.getMapper(AnswerRateBinder.class);

    AnswerRate bind(AnswerRateTO source);

    AnswerRateTO bind(AnswerRate source);
}
