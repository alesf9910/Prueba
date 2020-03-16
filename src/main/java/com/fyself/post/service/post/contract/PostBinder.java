package com.fyself.post.service.post.contract;

import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.subentities.*;
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
        if (source instanceof SurveyContentTO)
            return this.bind((SurveyContentTO) source);
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
        if (source instanceof SurveyContent)
            return this.bind((SurveyContent) source);
        return null;
    }

    LinkContentTO bind(LinkContent source);
    ImageContentTO bind(ImageContent source);
    TextContentTO bind(TextContent source);

    default SurveyContent bind(SurveyContentTO source) {
        if (source instanceof ChoiceSurveyTO)
            return this.bind((ChoiceSurveyTO) source);
        if (source instanceof HierarchySurveyTO)
            return this.bind((HierarchySurveyTO) source);
        if (source instanceof RateSurveyTO)
            return this.bind((RateSurveyTO) source);
        return bindSurvey(source);
    }

    ChoiceSurvey bind(ChoiceSurveyTO source);
    HierarchySurvey bind(HierarchySurveyTO source);
    RateSurvey bind(RateSurveyTO source);
    SurveyContent bindSurvey(SurveyContentTO source);

    default SurveyContentTO bind(SurveyContent source) {
        if (source instanceof ChoiceSurvey)
            return this.bind((ChoiceSurvey) source);
        if (source instanceof HierarchySurvey)
            return this.bind((HierarchySurvey) source);
        if (source instanceof RateSurvey)
            return this.bind((RateSurvey) source);
        return bindSurvey(source);
    }

    ChoiceSurveyTO bind(ChoiceSurvey source);
    HierarchySurveyTO bind(HierarchySurvey source);
    RateSurveyTO bind(RateSurvey source);
    SurveyContentTO bindSurvey(SurveyContent source);

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
