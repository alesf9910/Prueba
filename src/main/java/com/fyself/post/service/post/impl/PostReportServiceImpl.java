package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostReportService;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.PostReportRepository;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.service.post.contract.PostReportBinder.POST_REPORT_BINDER;
import static com.fyself.post.tools.LoggerUtils.*;
import static reactor.core.publisher.Mono.error;

@Service("postReportService")
@Validated
public class PostReportServiceImpl implements PostReportService {

    final PostReportRepository repository;
    final PostRepository postRepository;
    final Long maxReport;

    public PostReportServiceImpl(PostReportRepository repository, PostRepository postRepository,
                                 @Value("${mspost.application.report.max}") Long maxReport) {
        this.repository = repository;
        this.postRepository = postRepository;
        this.maxReport = maxReport;
    }

    @Override
    public Mono<String> add(PostReportTO to, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> repository.save(POST_REPORT_BINDER.bind(to
                        .withOwner(userId)
                        .withCreateAt()
                        .withUpdateAt())))
                .flatMap(postReport -> repository.countAllByPost(postReport.getPost().getId())
                        .filter(countReports -> countReports >= maxReport)
                        .flatMap(ignored -> postRepository.findById(postReport.getPost().getId()))
                        .flatMap(post -> postRepository.save(POST_BINDER.bindBlocked(post)))
                        .thenReturn(postReport))
                .doOnSuccess(postReport -> createEvent(postReport, context))
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(DomainEntity::getId);
    }

    @Override
    public Mono<Void> update(PostReportTO to, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> repository.getById(to.getId()))
                .flatMap(postReport -> repository.save(POST_REPORT_BINDER.set(postReport, to.withUpdateAt()))
                        .doOnSuccess(entity -> updateEvent(postReport, entity, context))
                )
                .switchIfEmpty(error(EntityNotFoundException::new))
                .then();
    }

    @Override
    public Mono<Void> delete(String id, FySelfContext context) {
        return context.authenticatedId()
                .flatMap(userId -> repository.getById(id)
                        .switchIfEmpty(error(EntityNotFoundException::new))
                        .flatMap(postReport ->
                                repository.delete(postReport)
                                        .doOnSuccess(entity -> deleteEvent(postReport, context)).then()
                        )
                );
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
