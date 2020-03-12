package com.fyself.post.facade.impl;

import com.fyself.post.facade.PostFacade;
import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

@Facade("postFacade")
public class PostFacadeImpl implements PostFacade {

    private final PostService service;

    public PostFacadeImpl(PostService service) {
        this.service = service;
    }

    @Override
    public Mono<Result<String>> create(PostTO to, FySelfContext context) {
        return service.create(to, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PostTO>> load(String post, FySelfContext context) {
        return service.load(post, context).map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> delete(String post, FySelfContext context) {
        return service.delete(post, context).map(Result::successful);
    }
}
