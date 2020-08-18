package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.AnswerAskTO;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerAsk;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


/**
 * Interface binder for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Mapper
public interface AnswerAskBinder {
    AnswerAskBinder ANSWER_ASK_BINDER = Mappers.getMapper(AnswerAskBinder.class);

    void bind(@MappingTarget AnswerAskTO target, AnswerAsk source);

    void bind(@MappingTarget AnswerAsk target, AnswerAskTO source);

    AnswerAsk bind(AnswerAskTO source);

    AnswerAskTO bind(AnswerAsk source);
}
