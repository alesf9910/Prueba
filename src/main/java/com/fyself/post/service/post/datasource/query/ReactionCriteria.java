package com.fyself.post.service.post.datasource.query;

import com.fyself.post.service.post.datasource.domain.PostReport;
import com.fyself.seedwork.service.repository.mongodb.criteria.DomainCriteria;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.Set;


public class ReactionCriteria extends DomainCriteria<PostReport> {
    private static final long serialVersionUID = -1173576727652462350L;


    public ReactionCriteria() {
        super(PostReport.class);
    }

    @Override
    protected Criteria force() {
        return null;
    }

    @Override
    public Set<String> searchField() {
        return Set.of();
    }
}
