package com.fyself.post.facade.impl;

import com.fyself.post.facade.PostReactionFacade;
import com.fyself.post.service.post.ReactionService;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.ReactionTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import java.util.Map;

@Facade("postReactionFacade")
public class PostReactionFacadeImpl implements PostReactionFacade {

    final ReactionService service;

    public PostReactionFacadeImpl(ReactionService service) {
        this.service = service;
    }


    @Override
    public Mono<Result<String>> create(ReactionTO to, FySelfContext context) {
        return service.add(to,context).map(Result::successful);
    }
    @Override
    public Mono<Result<Void>> update(ReactionTO to, FySelfContext context) {
        return service.update(to,context).map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> delete(String id, FySelfContext context) {
        return service.delete(id,context).map(Result::successful);
    }

    @Override
    public Mono<Result<Map>> load(String id, FySelfContext context) {
        return service.loadAll(id,context).map(Result::successful);
    }


}
