package com.fyself.post.facade.impl;

import com.fyself.post.facade.PostReactionFacade;
import com.fyself.post.service.post.ReactionService;
import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.post.service.post.contract.to.ReactionTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import java.util.Map;

import static reactor.core.publisher.Mono.error;

@Facade("postReactionFacade")
public class PostReactionFacadeImpl implements PostReactionFacade {

    final ReactionService service;
    final PostRepository postRepository;

    public PostReactionFacadeImpl(ReactionService service, PostRepository postRepository) {
        this.service = service;
        this.postRepository = postRepository;
    }


    @Override
    public Mono<Result<String>> create(ReactionTO to, FySelfContext context) {
        return postRepository.findById(to.getPost())
                .flatMap(post -> {
                    if(!post.getWorkspace())
                        return service.add(to,context).map(Result::successful);
                    else
                        return service.addWS(to.putEnterprise(post.getEnterprise()),context).map(Result::successful);

                })
                .switchIfEmpty(error(EntityNotFoundException::new))
                .onErrorResume(throwable -> error(EntityNotFoundException::new));
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
