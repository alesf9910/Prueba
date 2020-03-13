package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.subentities.Content;
import com.fyself.post.service.post.datasource.domain.subentities.ImageContent;
import com.fyself.post.service.post.datasource.domain.subentities.LinkContent;
import com.fyself.post.service.post.datasource.domain.subentities.TextContent;
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

    @Mapping(target = "content", expression = "java(bind(source.getContent()))")
    PostTO bind(Post source);

    default Content bind(ContentTO source) {
        if (source instanceof LinkContentTO)
            return this.bind((LinkContentTO) source);
        if (source instanceof ImageContentTO)
            return this.bind((ImageContentTO) source);
        if (source instanceof TextContentTO)
            return this.bind((TextContentTO) source);
        return null;
    }

    LinkContent bind(LinkContentTO source);
    ImageContent bind(ImageContentTO source);
    TextContent bind(TextContentTO source);

    default ContentTO bind(Content source) {
        if (source instanceof LinkContent)
            return this.bind((LinkContent) source);
        if (source instanceof ImageContent)
            return this.bind((ImageContent) source);
        if (source instanceof TextContent)
            return this.bind((TextContent) source);
        return null;
    }

    LinkContentTO bind(LinkContent source);
    ImageContentTO bind(ImageContent source);
    TextContentTO bind(TextContent source);

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
