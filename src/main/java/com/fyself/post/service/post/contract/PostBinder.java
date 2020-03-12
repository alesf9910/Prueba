package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.ContentTO;
import com.fyself.post.service.post.contract.to.LinkContentTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.subentities.Content;
import com.fyself.post.service.post.datasource.domain.subentities.LinkContent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import static java.util.stream.Collectors.toSet;

/**
 * Binder
 *
 * @author jmmarin
 * @since 0.0.1
 */
@Mapper
public interface PostBinder {
    PostBinder POST_BINDER = Mappers.getMapper(PostBinder.class);

    default Post bind(PostTO source) {
        Post post = new Post();
        post.setContents(source.getContents().stream().map(this::bind).collect(toSet()));
        return post;
    }

    default Content bind(ContentTO source) {
        if (source instanceof LinkContentTO)
            return this.bind((LinkContentTO) source);
        return null;
    }

    LinkContent bind(LinkContentTO source);
}
