package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.ReactionService;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.ReactionTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.ReactionRepository;
import com.fyself.post.service.post.datasource.domain.Reaction;
import com.fyself.post.service.post.datasource.domain.enums.ReactionType;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static com.fyself.post.service.post.contract.ReactionBinder.REACTION_BINDER;
import static com.fyself.post.tools.LoggerUtils.createEvent;
import static reactor.core.publisher.Mono.error;

@Service("reactionService")
@Validated
public class ReactionServiceImpl implements ReactionService {

    final ReactionRepository repository;

    public ReactionServiceImpl(ReactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> add(@Valid ReactionTO to, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> repository.save(REACTION_BINDER.bind(to
                        .withOwner(userId)
                        .withReportId(userId+"-"+to.getPost())
                        .withCreateAt()
                        .withUpdateAt())))
                .doOnSuccess(postReport -> createEvent(postReport, context))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }

    @Override
    public Mono<Void> update(@Valid ReactionTO to, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<Void> delete(String post, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PagedList<PostReportTO>> loadAll(PostReportCriteriaTO criteria, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<ReactionType> meReaction(String post, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> repository.findById(userId+"-"+post))
                .map(Reaction::getReaction);
    }
}
