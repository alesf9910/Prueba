package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostReportService;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.PostReportRepository;
import com.fyself.post.service.post.datasource.domain.PostReport;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.fyself.post.service.post.contract.PostReportBinder.POST_REPORT_BINDER;
import static com.fyself.post.tools.LoggerUtils.*;
import static com.fyself.seedwork.security.SecurityContextHolder.authenticatedId;
import static reactor.core.publisher.Mono.error;

@Service("postReportService")
@Validated
public class PostReportServiceImpl implements PostReportService {

    final PostReportRepository repository;

    public PostReportServiceImpl(PostReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> add(PostReportTO to, FySelfContext context) {
        return authenticatedId() //TODO @ACPU change
                .flatMap(userId ->
                        repository.save(POST_REPORT_BINDER.bind(to
                                .withOwner(userId)
                                .withId(userId) //TODO @ACPU remove
                                .withCreateAt()
                                .withUpdateAt())
                        )
                )
                .doOnSuccess(entity -> createEvent(entity, context))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }

    @Override
    public Mono<Void> update(PostReportTO to, FySelfContext context) {
        return authenticatedId().flatMap(userId -> repository.getById(to.getId()))
                .flatMap(
                        postReport -> repository.save(POST_REPORT_BINDER.set(postReport, to.withUpdateAt().withPost(postReport.getPost().getId())))
                                .doOnSuccess(entity -> updateEvent(postReport, entity, context))
                )
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<Void> delete(String id, FySelfContext context) {
        return authenticatedId().flatMap(userId ->
                repository.getById(id)
                        .switchIfEmpty(error(EntityNotFoundException::new))
                        .flatMap(
                                postReport -> repository.delete(postReport)
                                        .doOnSuccess(entity -> deleteEvent(postReport, context)).then()
                        ));
    }

    @Override
    public Mono<PostReportTO> load(String id, FySelfContext context) {
        return repository.getById(id)
                .map(POST_REPORT_BINDER::bind)
                .switchIfEmpty(error(EntityNotFoundException::new));
    }

    @Override
    public Mono<PagedList<PostReportTO>> loadAll(PostReportCriteriaTO criteria, FySelfContext context) {
        return repository.findPage(POST_REPORT_BINDER.bindToCriteria(criteria)).map(POST_REPORT_BINDER::bind);
    }
}
