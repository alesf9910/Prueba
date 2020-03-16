package com.fyself.post.facade.impl;

import com.fyself.post.facade.AnswerSurveyFacade;
import com.fyself.post.service.post.AnswerSurveyService;
import com.fyself.post.service.post.contract.to.AnswerSurveyTO;
import com.fyself.post.service.post.contract.to.criteria.AnswerSurveyCriteriaTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

@Facade("answerSurveyFacade")
public class AnswerSurveyFacadeImpl implements AnswerSurveyFacade {

    final AnswerSurveyService service;

    public AnswerSurveyFacadeImpl(AnswerSurveyService service) {
        this.service = service;
    }

    @Override
    public Mono<Result<String>> create(AnswerSurveyTO to, FySelfContext exchange) {
        return service.add(to, exchange).map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> update(AnswerSurveyTO template, FySelfContext context) {
        return service.update(template, context).thenReturn(Result.successful());
    }

    @Override
    public Mono<Result<Void>> delete(String id, FySelfContext context) {
        return service.delete(id, context).thenReturn(Result.successful());
    }

    @Override
    public Mono<Result<AnswerSurveyTO>> load(String id, String postId, FySelfContext context) {
        return service.load(id, postId, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<AnswerSurveyTO>>> search(AnswerSurveyCriteriaTO criteria, FySelfContext context) {
        return service.loadAll(criteria, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<AnswerSurveyTO>>> searchByMe(AnswerSurveyCriteriaTO criteria, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> service.loadAll(criteria.withOwner(userId), context))
                .map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<AnswerSurveyTO>>> searchToMe(AnswerSurveyCriteriaTO criteria, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> service.loadAll(criteria.withUser(userId), context))
                .map(Result::successful);
    }
}
