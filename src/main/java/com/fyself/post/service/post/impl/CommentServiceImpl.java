package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.CommentService;
import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.datasource.CommentRepository;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.fyself.post.service.post.contract.CommentBinder.COMMENT_BINDER;
import static com.fyself.post.tools.LoggerUtils.createEvent;
import static com.fyself.seedwork.security.SecurityContextHolder.authenticatedId;
import static reactor.core.publisher.Mono.error;

@Service("commentService")
@Validated
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    public CommentServiceImpl(CommentRepository repository) {
        this.repository = repository;
    }


    @Override
    public Mono<String> add(@NotNull @Valid CommentTO to, FySelfContext context) {
        return authenticatedId()
                .flatMap(userId -> repository.save(COMMENT_BINDER.bind(to.withUserId(userId).withCreatedAt().withUpdatedAt())))
                .doOnSuccess(entity -> createEvent(entity, context))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }


}
