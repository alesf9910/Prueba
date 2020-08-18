package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.AnswerRateTO;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerRate;
import org.mapstruct.Mapper;
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
