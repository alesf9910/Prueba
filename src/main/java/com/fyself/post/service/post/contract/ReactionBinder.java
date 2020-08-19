package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.ReactionTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.domain.PostReport;
import com.fyself.post.service.post.datasource.domain.Reaction;
import com.fyself.post.service.post.datasource.query.PostReportCriteria;
import com.fyself.seedwork.service.PagedList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * Interface binder for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Mapper
public interface ReactionBinder {
    ReactionBinder REACTION_BINDER = Mappers.getMapper(ReactionBinder.class);

    @Mapping(target = "post.id", source = "post")
    Reaction bind(ReactionTO source);

    @Mapping(target = "post", source = "post.id")
    ReactionTO bind(Reaction source);

}
