package com.fyself.post.facade;

import com.fyself.post.service.post.contract.to.AnswerSurveyTO;
import com.fyself.post.service.post.contract.to.criteria.AnswerSurveyCriteriaTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import java.util.HashMap;

/**
 * Facade interface for <i>Post report</i> operations.
 *
 * @author Alejandro
 * @since 0.0.1
 */
public interface AnswerSurveyFacade {
    Mono<Result<String>> create(AnswerSurveyTO template, FySelfContext exchange);
    Mono<Result<Void>> update(AnswerSurveyTO template, FySelfContext context);
    Mono<Result<Void>> patch(String id, HashMap to, FySelfContext context);
    Mono<Result<Void>> delete(String id, FySelfContext context);
    Mono<Result<AnswerSurveyTO>> load(String id, String postId, FySelfContext context);
    Mono<Result<PagedList<AnswerSurveyTO>>> search(AnswerSurveyCriteriaTO criteria, FySelfContext context);
    Mono<Result<PagedList<AnswerSurveyTO>>> searchByMe(AnswerSurveyCriteriaTO criteria, FySelfContext context);
    Mono<Result<PagedList<AnswerSurveyTO>>> searchToMe(AnswerSurveyCriteriaTO criteria, FySelfContext context);
}
