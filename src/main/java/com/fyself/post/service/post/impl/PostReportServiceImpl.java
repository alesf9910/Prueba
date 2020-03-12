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
import static com.fyself.post.tools.LoggerUtils.createEvent;
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
    public Mono<String> add(@NotNull @Valid PostReportTO to, FySelfContext context) {
    @Override
    public Mono<Void> update(PostReportTO to, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<Void> delete(String id, String post, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PostReportTO> load(String id, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PagedList<PostReportTO>> loadAll(PostReportCriteriaTO criteria, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PagedList<PostReportTO>> loadAllToMe(PostReportCriteriaTO criteria, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PagedList<PostReportTO>> loadAllFromMe(PostReportCriteriaTO criteria, FySelfContext context) {
        return null;
    }
}
