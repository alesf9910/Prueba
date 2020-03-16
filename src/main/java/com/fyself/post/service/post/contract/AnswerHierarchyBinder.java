package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.AnswerHierarchyTO;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerHierarchy;
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
public interface AnswerHierarchyBinder {
    AnswerHierarchyBinder ANSWER_HIERARCHY_BINDER = Mappers.getMapper(AnswerHierarchyBinder.class);

    @Mapping(target = "answers", source = "answer")
    AnswerHierarchy bind(AnswerHierarchyTO source);

    @Mapping(target = "answer", source = "answers")
    AnswerHierarchyTO bind(AnswerHierarchy source);
}
