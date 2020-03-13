package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.post.service.post.datasource.domain.Comment;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.subentities.*;
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
public interface AnswerAskBinder {
    AnswerAskBinder ANSWER_ASK_BINDER = Mappers.getMapper(AnswerAskBinder.class);

    void bind(@MappingTarget AnswerAskTO target, AnswerAsk source);

    void bind(@MappingTarget AnswerAsk target, AnswerAskTO source);

    AnswerAsk bind(AnswerAskTO source);

    AnswerAskTO bind(AnswerAsk source);
}
