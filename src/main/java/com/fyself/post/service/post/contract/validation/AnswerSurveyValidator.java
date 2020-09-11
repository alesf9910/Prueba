package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.PostTimelineRepository;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;
import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

/**
 * Check for update answer, if the user is not the owner or try to update a answer with different
 * type then return a 403 error
 *
 * @author Alejandro
 * @since 0.0.1
 */
public class AnswerSurveyValidator {

  private AnswerSurveyRepository repository;
  private PostService postService;
  private AnswerSurveyTO survey;
  private FySelfContext context;
  private PostTimelineRepository repositoryTimeline;

  public AnswerSurveyValidator(AnswerSurveyRepository repository, PostService postService,
      AnswerSurveyTO survey, FySelfContext context,
      PostTimelineRepository repositoryTimeline) {
    this.repository = repository;
    this.postService = postService;
    this.survey = survey;
    this.context = context;
    this.repositoryTimeline = repositoryTimeline;
  }

  public Mono<Boolean> isValidAnswer() {
    if (survey.getAnswer().getType().equals(TypeSurvey.SURVEY_ASK)) {
      return isValidAnswerAsk((AnswerAskTO) survey.getAnswer(), survey.getPost(), context)
          .zipWith(isAccess( survey.getPost()))
          .map(t -> t.getT2() && t.getT1());
    }

    if (survey.getAnswer().getType().equals(TypeSurvey.SURVEY_CHOICE)) {
      return isValidAnswerChoice((AnswerChoiceTO) survey.getAnswer(), survey.getPost(), context)
          .zipWith(isAccess( survey.getPost()))
          .map(t -> t.getT2() && t.getT1());
    }

    if (survey.getAnswer().getType().equals(TypeSurvey.SURVEY_HIERARCHY)) {
      return isValidHierarchy((AnswerHierarchyTO) survey.getAnswer(), survey.getPost(), context)
          .zipWith(isAccess( survey.getPost()))
          .map(t -> t.getT2() && t.getT1());
    }

    if (survey.getAnswer().getType().equals(TypeSurvey.SURVEY_RATE)) {
      return isValidRate((AnswerRateTO) survey.getAnswer(), survey.getPost(), context)
          .zipWith(isAccess( survey.getPost()))
          .map(t -> t.getT2() && t.getT1());
    }

    return just(false);
  }

  private Mono<Boolean> isAccess(String idPost) {

    return postService.load(idPost, context)
        .switchIfEmpty(error(EntityNotFoundException::new))
        .map(post -> post.getAccess().equals(Access.PUBLIC) || (post.getAccess().equals(Access.PRIVATE) && post.getOwner().equals(context.getAccount().get().getId())))
        .zipWith(repositoryTimeline.existsByPost_IdAndUser(idPost,context.getAccount().get().getId()))
        .map(t -> t.getT2() || t.getT1());

  }

  private Mono<Boolean> isValidRate(AnswerRateTO value, String id, FySelfContext context) {

    if (value.getAnswer() < 0) {
      return just(false);
    }

    return postService.load(id, context)
        .switchIfEmpty(error(EntityNotFoundException::new))
        .map(post -> post.getContent().getTypeContent().equals(TypeContent.SURVEY_RATE))
        .map(Boolean::booleanValue)
        .switchIfEmpty(just(false));
  }

  private Mono<Boolean> isValidHierarchy(AnswerHierarchyTO value, String id,
      FySelfContext context) {

    if (value.getAnswer().isEmpty()) {
      return just(false);
    }

    return postService.load(id, context)
        .switchIfEmpty(error(EntityNotFoundException::new))
        .map(post ->
            post.getContent().getTypeContent().equals(TypeContent.SURVEY_HIERARCHY)
        )
        .zipWith(postService.load(id, context)
            .map(post ->
                ((HierarchySurveyTO) post.getContent())
                    .getOptions().stream()
                    .anyMatch(map -> mapKeyComparator(map.values(), value.getAnswer().keySet()))
            )
        )
        .map(tuples -> tuples.getT1() && tuples.getT2())
        .map(Boolean::booleanValue)
        .switchIfEmpty(just(false));
  }

  private Mono<Boolean> isValidAnswerChoice(AnswerChoiceTO value, String id,
      FySelfContext context) {

    if (value.getAnswer().isEmpty()) {
      return just(false);
    }

    return postService.load(id, context)
        .switchIfEmpty(error(EntityNotFoundException::new))
        .map(post ->
            post.getContent().getTypeContent().equals(TypeContent.SURVEY_CHOICE)
        )
        .zipWith(postService.load(id, context)
            .map(post -> ((ChoiceSurveyTO) post.getContent())
                .getChoices().stream()
                .anyMatch(map -> mapKeyComparator(map.values(), value.getAnswer()))
            ))
        .map(tuples -> tuples.getT1() && tuples.getT2())
        .map(Boolean::booleanValue)
        .switchIfEmpty(just(false));
  }

  private Mono<Boolean> isValidAnswerAsk(AnswerAskTO value, String id, FySelfContext context) {

    if (value.getAnswer() == null || value.getAnswer().trim().equals("")) {
      return just(false);
    }

    return postService.load(id, context)
        .switchIfEmpty(error(EntityNotFoundException::new))
        .map(post ->
            post.getContent().getTypeContent().equals(TypeContent.SURVEY_ASK)
        )
        .map(Boolean::booleanValue)
        .switchIfEmpty(Mono.just(false));
  }

  private boolean mapKeyComparator(Collection<Object> values, Set<String> keySet) {
    if (values.isEmpty() || keySet.isEmpty()) {
      return false;
    }

    Set<String> target = new HashSet<>();
    values.forEach(o -> target.add(o.toString()));
    target.retainAll(keySet);

    return !target.isEmpty();
  }
}
