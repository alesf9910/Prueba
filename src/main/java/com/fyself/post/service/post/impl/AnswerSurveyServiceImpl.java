package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.AnswerSurveyService;
import com.fyself.post.service.post.contract.to.AnswerSurveyTO;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.AnswerSurveyCriteriaTO;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.fyself.post.service.post.contract.AnswerSurveyBinder.ANSWER_SURVEY_BINDER;
import static com.fyself.post.tools.LoggerUtils.createEvent;
import static reactor.core.publisher.Mono.error;

@Service("answerSurveyService")
@Validated
public class AnswerSurveyServiceImpl implements AnswerSurveyService {

    final AnswerSurveyRepository repository;

    public AnswerSurveyServiceImpl(AnswerSurveyRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> add(@Valid AnswerSurveyTO to, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> repository.save(
                        ANSWER_SURVEY_BINDER.bind(
                                to.withOwner(userId).withCreateAt().withUpdateAt())
                        )
                )
                .doOnSuccess(entity -> createEvent(entity, context))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }

    @Override
    public Mono<Void> update(AnswerSurveyTO to, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<Void> delete(String id, String post, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PostReportTO> load(String id, String post, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PagedList<PostReportTO>> loadAll(AnswerSurveyCriteriaTO criteria, FySelfContext context) {
        return null;
    }
}
