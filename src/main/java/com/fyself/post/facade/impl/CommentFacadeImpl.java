package com.fyself.post.facade.impl;

import com.fyself.post.facade.CommentFacade;
import com.fyself.post.service.post.CommentService;
import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.contract.to.criteria.CommentCriteriaTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.criteria.SortEntry;
import com.fyself.seedwork.service.repository.mongodb.criteria.enums.SortOrder;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.fyself.seedwork.facade.Result.successful;

@Facade("commentFacade")
public class CommentFacadeImpl implements CommentFacade {

    private final CommentService service;

    public CommentFacadeImpl(CommentService service) {
        this.service = service;
    }


    @Override
    public Mono<Result<String>> create(CommentTO to, FySelfContext context) {
        return service.add(to, context).map(Result::successful);
    }

    @Override
    public Mono<Result<CommentTO>> load(String id, String post, FySelfContext context) {
        return service.load(id, post, context).map(Result::successful);
    }

    @Override
    public Mono<Result<Void>> update(CommentTO to, FySelfContext context) {
        return service.update(to, context)
                .thenReturn(successful());
    }

    @Override
    public Mono<Result<Void>> delete(String id, String post, FySelfContext context) {
        return service.delete(id, post, context)
                .thenReturn(successful());
    }

    @Override
    public Mono<Result<PagedList<CommentTO>>> search(CommentCriteriaTO criteria, String post, FySelfContext context) {
        if (criteria.getSortCriteria()==null || criteria.getSortCriteria().isEmpty()) {
            SortEntry entry = new SortEntry();
            entry.setField("createdAt");
            entry.setOrder(SortOrder.DESC);
            criteria.setSortCriteria(List.of(entry));
        }
        return service.search(criteria, post, context).map(Result::successful);
    }

    @Override
    public Mono<Result<PagedList<CommentTO>>> searchAfter(CommentCriteriaTO criteria, String post, String id, FySelfContext context) {
        if (criteria.getSortCriteria()==null || criteria.getSortCriteria().isEmpty()) {
            SortEntry entry = new SortEntry();
            entry.setField("createdAt");
            entry.setOrder(SortOrder.DESC);
            criteria.setSortCriteria(List.of(entry));
        }
        return service.searchAfter(criteria, post, id, context).map(Result::successful);
    }
}
