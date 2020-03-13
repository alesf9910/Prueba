package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.ContentTO;
import com.fyself.post.service.post.contract.to.LinkContentTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.subentities.Content;
import com.fyself.post.service.post.datasource.domain.subentities.LinkContent;
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
public interface PostBinder {
    PostBinder POST_BINDER = Mappers.getMapper(PostBinder.class);

    @Mapping(target = "content", expression = "java(bind(source.getContent()))")
    Post bind(PostTO source);

    default Content bind(ContentTO source) {
        if (source instanceof LinkContentTO)
            return this.bind((LinkContentTO) source);
        return null;
    }

    LinkContent bind(LinkContentTO source);

    default PostTO bind(Post source) {
        PostTO post = new PostTO();
        post.setContent(this.bind(source.getContent()));
        return post;
    }

    default ContentTO bind(Content source) {
        if (source instanceof LinkContent)
            return this.bind((LinkContent) source);
        return null;
    }

    LinkContentTO bind(LinkContent source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "content", expression = "java(bind(source.getContent()))")
    void bind(@MappingTarget Post target, PostTO source);

    default Post set(Post target, PostTO source) {
        this.bind(target, source);
        return target;
    }
}
