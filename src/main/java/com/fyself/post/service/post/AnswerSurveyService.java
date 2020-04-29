package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.AnswerSurveyTO;
import com.fyself.post.service.post.contract.to.criteria.AnswerSurveyCriteriaTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashMap;

public interface AnswerSurveyService {
    Mono<String> add(@Valid AnswerSurveyTO to, FySelfContext context);

    Mono<Void> update(AnswerSurveyTO to, FySelfContext context);

    Mono<AnswerSurveyTO> patch(String id, HashMap to, FySelfContext context);

    Mono<Void> delete(String id, FySelfContext context);

    Mono<AnswerSurveyTO> load(String id, String post, FySelfContext context);

    Mono<PagedList<AnswerSurveyTO>> loadAll(AnswerSurveyCriteriaTO criteria, FySelfContext context);

    Mono<PagedList<AnswerSurveyTO>> loadAllByMe(AnswerSurveyCriteriaTO criteria, FySelfContext context);

    Mono<Void> sync(FySelfContext context);
}
