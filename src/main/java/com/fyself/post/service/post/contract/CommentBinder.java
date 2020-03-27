package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.contract.to.criteria.CommentCriteriaTO;
import com.fyself.post.service.post.datasource.domain.Comment;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.query.CommentCriteria;
import com.fyself.seedwork.service.PagedList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Binder
 *
 * @author jmmarin
 * @since 0.0.1
 */
@Mapper
public interface CommentBinder {
    CommentBinder COMMENT_BINDER = Mappers.getMapper(CommentBinder.class);

    @Mapping(target = "post.id", source = "post")
    @Mapping(target = "father", expression = "java(binFather(source.getFather()))")
    Comment bind(CommentTO source);

    @Mapping(target = "post", source = "post.id")
    @Mapping(target = "father", source = "father.id")
    @Mapping(target = "childrens", ignore = true)
    CommentTO bind(Comment source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "father", ignore = true)
    void bind(@MappingTarget Comment target, CommentTO source);

    default Comment set(Comment target, CommentTO source) {
        this.bind(target, source);
        return target;
    }

    @Mapping(target = "post.id", ignore = true)
    @Mapping(target = "father", expression = "java(binFather(source.getFather()))")
    CommentCriteria bind(CommentCriteriaTO source);

    default CommentCriteria bindToCriteria(CommentCriteriaTO source, String postId) {
        CommentCriteria criteria = this.bind(source);
        Post post = new Post();
        post.setId(postId);
        criteria.setPost(post);
        return criteria;
    }

    default PagedList<CommentTO> bindPage(Page<Comment> source) {
        List<CommentTO> profiles = source.stream().map(this::bind).collect(Collectors.toList());
        return new PagedList<>(profiles, 0, source.getTotalPages(), source.getTotalElements());
    }

    default Comment binFather(String father) {
        if (father != null) {
            var comment = new Comment();
            comment.setId(father);
            return comment;
        }
        return null;
    }

    default CommentCriteria bindToFatherCriteria(String fatherId) {
        CommentCriteria criteria = new CommentCriteria();
        var father = new Comment();
        father.setId(fatherId);
        criteria.setFather(father);
        criteria.setPage(0);
        criteria.setSize(5);
        return criteria;
    }

    default CommentTO bindPageOfChildren(Page<Comment> source, Comment entity) {
        List<CommentTO> childrens = source.stream().map(this::bind).collect(Collectors.toList());
        CommentTO commentTO = this.bind(entity);
        commentTO.setChildrens(childrens);
        return commentTO;
    }
}
