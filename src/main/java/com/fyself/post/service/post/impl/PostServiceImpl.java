package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.tools.LoggerUtils.deleteEvent;
import static reactor.core.publisher.Mono.error;

@Service("postService")
@Validated
public class PostServiceImpl implements PostService {
    private final PostRepository repository;

    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> create(@NotNull @Valid PostTO to, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> repository.save(POST_BINDER.bind(to.withUserId(userId).withCreatedAt().withUpdatedAt())).map(DomainEntity::getId));
    }

    @Override
    public Mono<Void> update(@NotNull @Valid PostTO to, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PostTO> load(@NotNull String id, FySelfContext context) {
        return repository.findById(id).map(POST_BINDER::bind);
    }

    @Override
    public Mono<Void> delete(@NotNull String id, FySelfContext context) {
        return repository.findById(id)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .flatMap(post -> repository.softDelete(post).doOnSuccess(entity -> deleteEvent(post, context)))
                .then();
    }
}
