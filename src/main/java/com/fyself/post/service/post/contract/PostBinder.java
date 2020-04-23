package com.fyself.post.service.post.contract;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.contract.to.criteria.PostCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.PostTimeline;
import com.fyself.post.service.post.datasource.domain.subentities.*;
import com.fyself.post.service.post.datasource.query.PostCriteria;
import com.fyself.post.service.post.datasource.query.PostTimelineCriteria;
import com.fyself.seedwork.service.PagedList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.fyself.seedwork.util.JsonUtil.MAPPER;
import static java.util.stream.Collectors.toList;

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
    @Mapping(target = "sharedWith", ignore = true)
    @Mapping(target = "blocked", constant = "false")
    Post bind(PostTO source);

    @Mapping(target = "content", expression = "java(bind(source.getContent()))")
    PostTO bind(Post source);

    default PostTO bindPostWithAnswer(Post source, AnswerSurveyTO answerSurveyTO) {
        var postTO = bind(source);
        ((SurveyContentTO) postTO.getContent()).setAnswer(answerSurveyTO);
        return postTO;
    }

    default PostTO bindPostTOWithAnswer(PostTO source, AnswerSurveyTO answerSurveyTO) {
        if (source.getContent() != null)
            ((SurveyContentTO) source.getContent()).setAnswer(answerSurveyTO);
        return source;
    }

    default Content bind(ContentTO source) {
        if (source instanceof AwardContentTO)
            return this.bind((AwardContentTO) source);
       if (source instanceof ProfileContentTO)
            return this.bind((ProfileContentTO) source);
        if (source instanceof TextContentTO)
            return this.bind((TextContentTO) source);
        if (source instanceof SurveyContentTO)
            return this.bind((SurveyContentTO) source);
        return null;
    }

    AwardContent bind(AwardContentTO source);

    ProfileContent bind(ProfileContentTO source);

    TextContent bind(TextContentTO source);

    default ContentTO bind(Content source) {
        if (source instanceof AwardContent)
            return this.bind((AwardContent) source);
        if (source instanceof ProfileContent)
            return this.bind((ProfileContent) source);
        if (source instanceof TextContent)
            return this.bind((TextContent) source);
        if (source instanceof SurveyContent)
            return this.bind((SurveyContent) source);
        return null;
    }

    AwardContentTO bind(AwardContent source);

    ProfileContentTO bind(ProfileContent source);

    TextContentTO bind(TextContent source);

    default SurveyContent bind(SurveyContentTO source) {
        if (source instanceof ChoiceSurveyTO)
            return this.bind(((ChoiceSurveyTO) source).generateChoicesIds());
        if (source instanceof HierarchySurveyTO)
            return this.bind(((HierarchySurveyTO) source).generateOptionsIds());
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
    @Mapping(target = "sharedWith", ignore = true)
    @Mapping(target = "content", expression = "java(set(target.getContent(), source.getContent()))")
    void bind(@MappingTarget Post target, PostTO source);

    default Content set(Content target, ContentTO source) {
        if (source instanceof AwardContentTO)
            return this.bind((AwardContentTO) source);
        if (source instanceof ProfileContentTO)
            return this.bind((ProfileContentTO) source);
        if (source instanceof TextContentTO)
            return this.bind((TextContentTO) source);
        if (source instanceof SurveyContentTO)
            return this.set((SurveyContent) target, (SurveyContentTO) source);
        return null;
    }

    default SurveyContent set(SurveyContent target, SurveyContentTO source) {
        if (source instanceof ChoiceSurveyTO)
            return this.setChoice((ChoiceSurvey) target, (ChoiceSurveyTO) source);
        if (source instanceof HierarchySurveyTO)
            return this.setOptions((HierarchySurvey) target, (HierarchySurveyTO) source);
        if (source instanceof RateSurveyTO)
            return this.bind((RateSurveyTO) source);
        return bindSurvey(source);
    }

    default ChoiceSurvey setChoice(ChoiceSurvey target, ChoiceSurveyTO source) {
        for (Map<String, Object> choice : source.getChoices()) {
            if (choice.containsKey("id")) {
                target.setChoices(target.getChoices().stream()
                        .map(map -> {
                            if (map.get("id").equals(choice.get("id")))
                                return choice;
                            else
                                return map;
                        })
                        .collect(Collectors.toSet()));
            } else {
                choice.put("id", UUID.randomUUID().toString());
                target.getChoices().add(choice);
            }
        }
        for (Iterator<Map<String, Object>> it = target.getChoices().iterator(); it.hasNext(); ) {
            AtomicReference<Boolean> deleted = new AtomicReference<>(true);
            Map<String, Object> choice = it.next();
            source.getChoices().stream()
                    .filter(map -> map.get("id").equals(choice.get("id")))
                    .map(map -> {
                        deleted.set(false);
                        return map;
                    })
                    .collect(Collectors.toSet());
            if (deleted.get())
                it.remove();
        }
        return target;
    }

    default HierarchySurvey setOptions(HierarchySurvey target, HierarchySurveyTO source) {
        for (Map<String, Object> choice : source.getOptions()) {
            if (choice.containsKey("id")) {
                target.setOptions(target.getOptions().stream()
                        .map(map -> {
                            if (map.get("id").equals(choice.get("id")))
                                return choice;
                            else
                                return map;
                        })
                        .collect(Collectors.toSet()));
            } else {
                choice.put("id", UUID.randomUUID().toString());
                target.getOptions().add(choice);
            }
        }
        for (Iterator<Map<String, Object>> it = target.getOptions().iterator(); it.hasNext(); ) {
            AtomicReference<Boolean> deleted = new AtomicReference<>(true);
            Map<String, Object> choice = it.next();
            source.getOptions().stream()
                    .filter(map -> map.get("id").equals(choice.get("id")))
                    .map(map -> {
                        deleted.set(false);
                        return map;
                    })
                    .collect(Collectors.toSet());
            if (deleted.get())
                it.remove();
        }
        return target;
    }

    default Post set(Post target, PostTO source) {
        this.bind(target, source);
        return target;
    }

    default PostTO pacth(Post post, HashMap to) {
        try {
            var c = MAPPER.updateValue(this.bind(post), to);
            return c;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        }
        return bind(post);
    }

    PostCriteria bindToCriteria(PostCriteriaTO source);

    default PostCriteriaTO bindToCriteriaTO(PostTimelineCriteriaTO source) {
        var criteria = new PostCriteriaTO();
        criteria.setPage(source.getPage());
        criteria.setSize(source.getSize());
        criteria.setType(source.getType());
        return criteria;
    }

    default PostCriteria bindToCriteria(PostTimelineCriteriaTO source) {
        var criteria = new PostCriteria();
        criteria.setOwner(source.getUser());
        criteria.setPage(source.getPage());
        criteria.setSize(source.getSize());
        return criteria;
    }

    default PagedList<PostTO> bindPage(Page<Post> source) {
        List<PostTO> postTOS = source.stream().map(this::bind).collect(toList());
        return new PagedList<>(postTOS, source.getNumber(), source.getTotalPages(), source.getTotalElements());
    }

    PostTimelineCriteria bindToTimelineCriteria(PostTimelineCriteriaTO source);

    default PagedList<PostTO> bindPageTimeline(Page<PostTimeline> source, String userId) {
        List<PostTO> postTOS = source.stream()
                .map(PostTimeline::getPost)
                .map(this::emptyContent)
                .map(this::bind)
                .collect(toList());
        return new PagedList<>(postTOS, source.getNumber(), source.getTotalPages(), source.getTotalElements());
    }

    default Post bindBlocked(Post post) {
        post.setBlocked(true);
        return post;
    }

    default Post bindShareWith(Post post, PostShareTO to) {
        return post.shareUser(to.getSharedWith());
    }

    default Post bindShareBulk(Post post, PostShareBulkTO to) {
        return post.shareBulk(to.getSharedWith());
    }

    default Post bindStopShareWith(Post post, PostShareTO to) {
        return post.stopShareUser(to.getSharedWith());
    }

    private Post emptyContent(Post post) {
        if (post.isBlocked() || post.getDeleted())
            return post.withContent(null);
        return post;
    }

    default PagedList<PostTO> bind(PagedList<PostTO> postTOPagedList, List<PostTO> postTOS) {
        postTOPagedList.setElements(postTOS);
        return postTOPagedList;
    }
}
