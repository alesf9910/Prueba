package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service("postService")
@Validated
public class PostServiceImpl implements PostService {
    @Override
    public Mono<String> create(@NotNull @Valid PostTO to, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<Void> update(@NotNull @Valid PostTO to, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PostTO> load(@NotNull String id, FySelfContext context) {
        return Mono.just(new PostTO().withOwner("5e5e9440e3e14e1c49d8ebbf1"));
    }

    @Override
    public Mono<Void> delete(@NotNull String id, FySelfContext context) {
        return null;
    }
}
