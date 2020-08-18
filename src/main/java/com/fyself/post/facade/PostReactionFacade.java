package com.fyself.post.facade;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.ReactionTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.domain.Reaction;
import com.fyself.post.service.post.datasource.domain.enums.ReactionType;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

public class PostReactionFacade {

    public Mono<Result<String>> create(ReactionTO to, FySelfContext context) {
        return Mono.just("mock-id").map(Result::successful);
    }

    public Mono<Result<ReactionTO>> load(String id, FySelfContext context) {
        ReactionTO response = new ReactionTO();
        response.setPost("mock-id");
        response.setReaction(ReactionType.LIKE_IT);
        response.setOwner(context.getAccount().get().getId());
        return Mono.just(response).map(Result::successful);
    }

    public Mono<Result<Void>> update(PostReportTO withReportId, FySelfContext context) {
        return Mono.just(Result.successful());
    }

    public Mono<Result<Void>> delete(String id, FySelfContext context) {
        return Mono.just(Result.successful());
    }

    public Mono<Result<Object>> search(PostReportCriteriaTO to, FySelfContext context) {
        return null;
    }
}
