package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.domain.PostReport;
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
public interface PostReportBinder {
    PostReportBinder POST_REPORT_BINDER = Mappers.getMapper(PostReportBinder.class);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "reason", source = "reason")
    @Mapping(target = "post.id", source = "post")
    PostReport bind(PostReportTO source);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "reason", source = "reason")
    @Mapping(target = "post", source = "post.id")
    PostReportTO bind(PostReport source);

    @Mapping(target = "post.id", source = "post")
    void bind(@MappingTarget PostReport target, PostReportTO source);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "post.id", source = "post")
    PostReportCriteria bind(PostReportCriteriaTO source);


    default PostReportCriteria bindToCriteria(PostReportCriteriaTO source) {

        if (source.getPost() == null || source.getPost().trim().equals("")) {
            return buildPostReportCriteria(source);
        }

        return this.bind(source);
    }

    private PostReportCriteria buildPostReportCriteria(PostReportCriteriaTO source) {
        PostReportCriteria criteria = new PostReportCriteria();
        criteria.setOwner(source.getOwner());
        criteria.setUser(source.getUser());
        return criteria;
    }

    default PostReport set(PostReport target, PostReportTO source) {
        this.bind(target, source);
        return target;
    }


    default PagedList<PostReportTO> bind(Page<PostReport> source) {
        List<PostReportTO> packagesBinder = source.map(this::bind).getContent();
        return new PagedList<>(packagesBinder, source.getNumber(), source.getTotalPages(), source.getTotalElements());
    }

}
