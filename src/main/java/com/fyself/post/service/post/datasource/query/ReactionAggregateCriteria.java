package com.fyself.post.service.post.datasource.query;

import com.fyself.post.service.post.contract.to.ReactionResumeTO;
import com.fyself.post.service.post.datasource.domain.Reaction;
import com.fyself.seedwork.service.repository.mongodb.criteria.AggregationCriteria;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class ReactionAggregateCriteria extends AggregationCriteria<Reaction, ReactionResumeTO> {

    private static final long serialVersionUID = -7409173155561634215L;

    private String id;

    public ReactionAggregateCriteria(String id) {
        super(Reaction.class, ReactionResumeTO.class);
        this.id = id;
    }

    @Override
    public List<AggregationOperation> getAggOperations() {
        return List.of(
                Aggregation.match(Criteria.where("post").is(id)),
                Aggregation.group("reaction").count().as("total"),
                Aggregation.project("total").and("reaction").previousOperation(),
                Aggregation.sort(Sort.Direction.DESC, "total")
        );
    }
}
