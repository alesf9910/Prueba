package com.fyself.post.facade.impl;

import com.fyself.post.facade.CommentFacade;
import com.fyself.post.service.post.CommentService;
import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.facade.Result.successful;

@Facade("commentFacade")
public class CommentFacadeImpl implements CommentFacade {

    private final CommentService service;

    public CommentFacadeImpl(CommentService service) {
        this.service = service;
    }


    @Override
    public Mono<Result<String>> create(CommentTO to, FySelfContext context) {
        return service.add(to, context).map(Result::successful);
    }

    @Override
    public Mono<Result<CommentTO>> load(String id, String post, FySelfContext context) {
        return service.load(id, post, context).map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> update(CommentTO to, FySelfContext context) {
        return service.update(to, context)
                .thenReturn(successful());
    }

    @Override
    public Mono<Result<Void>> delete(String id, String post, FySelfContext context) {
        return service.delete(id, post, context)
                .thenReturn(successful());
    }


}
