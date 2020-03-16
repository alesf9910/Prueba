package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.CommentService;
import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.contract.to.criteria.CommentCriteriaTO;
import com.fyself.post.service.post.datasource.CommentRepository;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.fyself.post.service.post.contract.CommentBinder.COMMENT_BINDER;
import static com.fyself.post.tools.LoggerUtils.*;
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

    @Override
    public Mono<CommentTO> load(@NotNull String id, String post, FySelfContext context) {
        return repository.getById(id)
                .filter(comment -> comment.getPost().getId().equals(post))
                .map(COMMENT_BINDER::bind)
                .switchIfEmpty(error(EntityNotFoundException::new));
    }

    @Override
    public Mono<Void> update(@NotNull @Valid CommentTO to, FySelfContext context) {
        return repository.findById(to.getId())
                .map(comment -> COMMENT_BINDER.set(comment, to.withUpdatedAt()))
                .flatMap(comment -> repository.save(comment)
                        .doOnSuccess(entity -> updateEvent(comment, entity, context)))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<Void> delete(@NotNull String id, String post, FySelfContext context) {
        return repository.findById(id)
                .filter(comment -> comment.getPost().getId().equals(post))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .flatMap(comment -> repository.softDelete(comment).doOnSuccess(entity -> deleteEvent(comment, context)))
                .then();
    }

    @Override
    public Mono<PagedList<CommentTO>> search(@NotNull CommentCriteriaTO criteria, String post, FySelfContext context) {
        return repository.findPage(COMMENT_BINDER.bindToCriteria(criteria, post))
                .map(COMMENT_BINDER::bindPage);
    }
}
