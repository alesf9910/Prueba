package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.datasource.domain.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

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
    Comment bind(CommentTO source);

    @Mapping(target = "post", source = "post.id")
    CommentTO bind(Comment source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "post", ignore = true)
    void bind(@MappingTarget Comment target, CommentTO source);

    default Comment set(Comment target, CommentTO source) {
        this.bind(target, source);
        return target;
    }
}
