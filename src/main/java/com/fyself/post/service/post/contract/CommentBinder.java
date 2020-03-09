package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.datasource.domain.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
}
