package com.fyself.post.service.post.contract;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.contract.to.criteria.AnswerSurveyCriteriaTO;
import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.subentities.*;
import com.fyself.post.service.post.datasource.query.AnswerSurveyCriteria;
import com.fyself.seedwork.service.PagedList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

import static com.fyself.post.service.post.contract.AnswerAskBinder.ANSWER_ASK_BINDER;
import static com.fyself.post.service.post.contract.AnswerChoiceBinder.ANSWER_CHOICE_BINDER;
import static com.fyself.post.service.post.contract.AnswerHierarchyBinder.ANSWER_HIERARCHY_BINDER;
import static com.fyself.post.service.post.contract.AnswerRateBinder.ANSWER_RATE_BINDER;
import static com.fyself.seedwork.util.JsonUtil.MAPPER;


/**
 * Interface binder for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Mapper
public interface AnswerSurveyBinder {
    AnswerSurveyBinder ANSWER_SURVEY_BINDER = Mappers.getMapper(AnswerSurveyBinder.class);

    @Mapping(target = "post.id", source = "post")
    @Mapping(target = "answer", expression = "java(buildSurveyAnswer(source))")
    AnswerSurvey bind(AnswerSurveyTO source);

    @Mapping(target = "post", source = "post.id")
    @Mapping(target = "answer", expression = "java(buildSurveyAnswer(source))")
    AnswerSurveyTO bindFromSurvey(AnswerSurvey source);

    default AnswerSurveyTO patch(AnswerSurvey target, HashMap to) {
        try {
            return MAPPER.updateValue(this.bindFromSurvey(target), to);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
        return bindFromSurvey(target);
    }

    default AnswerSurveyTO bind(AnswerSurvey target, AnswerTO source) {
        AnswerSurveyTO survey = bindFromSurvey(target);
        survey.setAnswer(source);
        survey.setCreatedAt(target.getCreatedAt());
        survey.withOwner(target.getOwner());
        survey.withAnswerId(target.getId());
        survey.setPost(target.getPost().getId());
        return survey;
    }

    default AnswerSurveyCriteria bind(AnswerSurveyCriteriaTO source) {
        AnswerSurveyCriteria criteria = new AnswerSurveyCriteria();

        if (source.getPost() != null) {
            Post post = new Post();
            post.setId(source.getPost());
            criteria.setPost(post);
        }

        criteria.setTypeSurvey(source.getTypeSurvey());
        criteria.setOwner(source.getOwner());
        criteria.setUser(source.getUser());
        criteria.setPostIds(source.getPostIds());
        return criteria;
    }

    default PagedList<AnswerSurveyTO> bind(Page<AnswerSurvey> source) {
        List<AnswerSurveyTO> packagesBinder = source.map(this::bindFromSurvey).getContent();
        return new PagedList<>(packagesBinder, source.getNumber(), source.getTotalPages(), source.getTotalElements());
    }

    default Answer buildSurveyAnswer(AnswerSurveyTO source) {
        switch (source.getAnswer().getType()) {
            case SURVEY_ASK:
                return ANSWER_ASK_BINDER.bind((AnswerAskTO) source.getAnswer());
            case SURVEY_RATE:
                return ANSWER_RATE_BINDER.bind((AnswerRateTO) source.getAnswer());
            case SURVEY_CHOICE:
                return ANSWER_CHOICE_BINDER.bind((AnswerChoiceTO) source.getAnswer());
            default:
                return ANSWER_HIERARCHY_BINDER.bind((AnswerHierarchyTO) source.getAnswer());
        }
    }

    default AnswerTO buildSurveyAnswer(AnswerSurvey source) {
        switch (source.getAnswer().getType()) {
            case SURVEY_ASK:
                return ANSWER_ASK_BINDER.bind((AnswerAsk) source.getAnswer());
            case SURVEY_RATE:
                return ANSWER_RATE_BINDER.bind((AnswerRate) source.getAnswer());
            case SURVEY_CHOICE:
                return ANSWER_CHOICE_BINDER.bind((AnswerChoice) source.getAnswer());
            default:
                return ANSWER_HIERARCHY_BINDER.bind((AnswerHierarchy) source.getAnswer());
        }
    }
}
