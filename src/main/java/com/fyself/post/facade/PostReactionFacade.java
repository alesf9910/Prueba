package com.fyself.post.facade;

import com.fyself.post.service.post.contract.to.ReactionTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.domain.enums.ReactionType;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface PostReactionFacade {

    Mono<Result<String>> create(ReactionTO to, FySelfContext context);
    Mono<Result<Void>> update(ReactionTO withReportId, FySelfContext context);
    Mono<Result<Void>> delete(String id, FySelfContext context);
    Mono<Result<Map>> load(String id, FySelfContext context);

}
