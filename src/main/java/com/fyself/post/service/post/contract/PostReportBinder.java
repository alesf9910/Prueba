package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.post.service.post.datasource.domain.Post;
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

    @Mapping(target = "post.id", source = "post")
    PostReport bind(PostReportTO source);

    @Mapping(target = "post", source = "post.id")
    PostReportTO bind(PostReport source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "post.id", source = "post")
    void bind(@MappingTarget PostReport target, PostReportTO source);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "post.id", source = "post")
    PostReportCriteria bind(PostReportCriteriaTO source);

    default PostReportCriteria bindToCriteria(PostReportCriteriaTO source) {
        return this.bind(source);
    }

    default PostReportCriteria bindToCriteria(PostReportCriteriaTO source, String postId) {
        PostReportCriteria criteria = this.bind(source);
        Post post = new Post();
        post.setId(postId);
        criteria.setPost(post);
        return criteria;
    }

    default PagedList<PostReportTO> bind(Page<PostReport> source) {
        List<PostReportTO> packagesBinder = source.map(this::bind).getContent();
        return new PagedList<>(packagesBinder, source.getNumber(), source.getTotalPages(), source.getTotalElements());
    }

}
