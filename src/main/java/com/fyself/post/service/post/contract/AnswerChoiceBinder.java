package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.AnswerAskTO;
import com.fyself.post.service.post.contract.to.AnswerChoiceTO;
import com.fyself.post.service.post.datasource.domain.subentities.Answer;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerAsk;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerChoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


/**
 * Interface binder for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Mapper
public interface AnswerChoiceBinder {
    AnswerChoiceBinder ANSWER_CHOICE_BINDER = Mappers.getMapper(AnswerChoiceBinder.class);

    @Mapping(target = "answers", source = "answer")
    AnswerChoice bind(AnswerChoiceTO source);

    @Mapping(target = "answer", source = "answers")
    AnswerChoiceTO bind(AnswerChoice source);
}
