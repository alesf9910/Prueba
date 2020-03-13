package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.contract.to.criteria.AnswerSurveyCriteriaTO;
import com.fyself.post.service.post.datasource.domain.AnswerSurvey;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;
import com.fyself.post.service.post.datasource.domain.subentities.*;
import com.fyself.post.service.post.datasource.query.AnswerSurveyCriteria;
import com.fyself.seedwork.service.PagedList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

import static com.fyself.post.service.post.contract.AnswerAskBinder.ANSWER_ASK_BINDER;
import static com.fyself.post.service.post.contract.AnswerChoiceBinder.ANSWER_CHOICE_BINDER;
import static com.fyself.post.service.post.contract.AnswerHierarchyBinder.ANSWER_HIERARCHY_BINDER;
import static com.fyself.post.service.post.contract.AnswerRateBinder.ANSWER_RATE_BINDER;


/**
 * Interface binder for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Mapper
public interface AnswerSurveyBinder {
    AnswerSurveyBinder ANSWER_SURVEY_BINDER = Mappers.getMapper(AnswerSurveyBinder.class);

    @Mapping(target = "post", expression = "java(buildPostWithId(source.getPost()))")
    @Mapping(target = "answer", expression = "java(buildSurveyAnswer(source))")
    AnswerSurvey bind(AnswerSurveyTO source);

    default AnswerSurveyTO bind(AnswerSurvey source) {
        return new AnswerSurveyTO(source.getPost().getId(), buildSurveyAnswer(source));
    }

    default AnswerSurveyCriteria bind(AnswerSurveyCriteriaTO source) {
        AnswerSurveyCriteria criteria = new AnswerSurveyCriteria();
        criteria.setPost(buildPostWithId(source.getPost()));

        Answer answer = new Answer() {
            @Override
            public void setType(TypeSurvey type) {
                super.setType(type);
            }
        };
        answer.setType(source.getAnswer().getType());

        criteria.setAnswer(answer);
        criteria.setOwner(source.getOwner());
        criteria.setUser(source.getUser());
        return criteria;
    }


    default PagedList<AnswerSurveyTO> bind(Page<AnswerSurvey> source) {
        List<AnswerSurveyTO> packagesBinder = source.map(this::bind).getContent();
        return new PagedList<>(packagesBinder, source.getNumber(), source.getTotalPages(), source.getTotalElements());
    }

    default Answer buildSurveyAnswer(AnswerSurveyTO source) {
        switch (source.getAnswer().getType()) {
            case ASK:
                return ANSWER_ASK_BINDER.bind((AnswerAskTO) source.getAnswer());
            case RATE:
                return ANSWER_RATE_BINDER.bind((AnswerRateTO) source.getAnswer());
            case CHOICE:
                return ANSWER_CHOICE_BINDER.bind((AnswerChoiceTO) source.getAnswer());
            default:
                return ANSWER_HIERARCHY_BINDER.bind((AnswerHierarchyTO) source.getAnswer());
        }
    }

    default AnswerTO buildSurveyAnswer(AnswerSurvey source) {
        switch (source.getAnswer().getType()) {
            case ASK:
                return ANSWER_ASK_BINDER.bind((AnswerAsk) source.getAnswer());
            case RATE:
                return ANSWER_RATE_BINDER.bind((AnswerRate) source.getAnswer());
            case CHOICE:
                return ANSWER_CHOICE_BINDER.bind((AnswerChoice) source.getAnswer());
            default:
                return ANSWER_HIERARCHY_BINDER.bind((AnswerHierarchy) source.getAnswer());
        }
    }

    default Post buildPostWithId(String postId) {
        Post post = new Post();
        post.setId(postId);
        return post;
    }
}
