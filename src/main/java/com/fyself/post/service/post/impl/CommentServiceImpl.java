package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.CommentService;
import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.contract.to.criteria.CommentCriteriaTO;
import com.fyself.post.service.post.datasource.CommentRepository;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.query.CommentCriteria;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Date;

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
    public Mono<String> add(@NotNull @Valid CommentTO to, String postOwner,FySelfContext context) {
        return authenticatedId()
                .flatMap(userId -> repository.save(COMMENT_BINDER.bind(to.withUserId(userId).withCreatedAt().withUpdatedAt())))
                .doOnSuccess(entity -> createEvent(entity, postOwner, context))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }

    @Override
    public Mono<String> addWS(@NotNull @Valid CommentTO to, FySelfContext context) {
        return authenticatedId()
                .flatMap(userId -> repository.save(COMMENT_BINDER.bind(to.withUserId(userId).withCreatedAt().withUpdatedAt())))
                .doOnSuccess(entity -> createEventWS(entity, context, to.getEnterprise()))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }

    @Override
    public Mono<CommentTO> load(@NotNull String id, String post, FySelfContext context) {
        return repository.getById(id)
                .filter(comment -> comment.getPost().getId().equals(post))
                .flatMap(comment -> repository.findPage(COMMENT_BINDER.bindToFatherCriteria(comment.getId()))
                        .map(comments -> COMMENT_BINDER.bindPageOfChildren(comments, comment)))
                .switchIfEmpty(error(EntityNotFoundException::new));
    }

    @Override
    public Mono<Void> update(@NotNull @Valid CommentTO to, FySelfContext context) {
        return repository.findById(to.getId())
                .map(comment -> COMMENT_BINDER.set(comment, to.withUpdatedAt()))
                .flatMap(comment -> repository.save(comment)
                        .doOnSuccess(entity -> updateEvent(entity, context)))
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
                .flatMap(pageComents -> Flux.fromIterable(pageComents)
                        .flatMap(comment -> repository.findPage(COMMENT_BINDER.bindToFatherCriteria(comment.getId())).map(comments -> COMMENT_BINDER.bindPageOfChildren(comments, comment)), 1)
                        .collectList()
                        .map(commentTOS -> COMMENT_BINDER.bindPage(commentTOS, pageComents)));
    }

    @Override
    public Mono<PagedList<CommentTO>> searchBefore(@NotNull CommentCriteriaTO criteria, String post, String id, FySelfContext context) {
        return repository.findById(id).flatMap(comment -> allComentBefore(criteria,post,id,comment.getCreatedAt(),context));
    }

    public Mono<PagedList<CommentTO>> allComentBefore(@NotNull CommentCriteriaTO criteria, String post, String id, LocalDateTime createAt, FySelfContext context) {
        return repository.findPage(COMMENT_BINDER.bindToCriteria(criteria, post, createAt))
                .flatMap(pageComents -> Flux.fromIterable(pageComents)
                        .flatMap(comment -> repository.findPage(COMMENT_BINDER.bindToFatherCriteria(comment.getId())).map(comments -> COMMENT_BINDER.bindPageOfChildren(comments, comment)), 1)
                        .collectList()
                        .map(commentTOS -> COMMENT_BINDER.bindPage(commentTOS, pageComents)));
    }

    @Override
    public Mono<Long> count(String post) {
        CommentCriteria criteria = new CommentCriteria();
        Post postP = new Post();
        postP.setId(post);
        criteria.setPost(postP);
        return repository.count(criteria);
    }
}
