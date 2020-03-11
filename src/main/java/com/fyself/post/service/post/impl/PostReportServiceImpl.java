package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostReportService;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.PostReportRepository;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service("postReportService")
@Validated
public class PostReportServiceImpl implements PostReportService {

    final PostReportRepository repository;

    public PostReportServiceImpl(PostReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> add(@NotNull @Valid PostReportTO to, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<Void> update(@NotNull @Valid PostReportTO to, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<Void> delete(@NotNull String id, String post, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PostReportTO> load(@NotNull String id, String post, FySelfContext context) {
        return null;
    }

    @Override
    public Mono<PagedList<PostReportTO>> loadAll(PostReportCriteriaTO criteria, String post, FySelfContext context) {
        return null;
    }
}
