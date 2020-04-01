package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;
import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.error.ErrorCode.FORBIDDEN_ACCESS;
import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

/**
 * Check for update answer, if the user is not the owner or try to update a answer with different type then return a 403 error
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class AnswerSurveyCreateValidator extends MonoBiValidatorFixInterceptor<AnswerSurveyTO, FySelfContext> {

    final AnswerSurveyRepository repository;
    final PostRepository postRepository;
    final PostService postService;

    public AnswerSurveyCreateValidator(AnswerSurveyRepository repository, PostRepository postRepository, PostService postService) {
        this.repository = repository;
        this.postRepository = postRepository;
        this.postService = postService;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around(
            "execution(public * com.fyself.post.service.post.AnswerSurveyService+.add(..)) ||" +
                    "execution(public * com.fyself.post.service.post.AnswerSurveyService+.upadte(..))"
    )
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "seed.error.rest.invalid_type_params");
    }

    @Override
    protected Mono<Boolean> validate(AnswerSurveyTO value, FySelfContext context) {
        if (value.getAnswer() == null) {
            return just(false);
        }

        if (value.getPost() == null) {
            return just(false);
        }

        return isValidAnswer(value, context);
    }

    private Mono<Boolean> isValidAnswer(AnswerSurveyTO value, FySelfContext context) {
        if (value.getAnswer().getType().equals(TypeSurvey.SURVEY_ASK)) {
            return isValidAnswerAsk((AnswerAskTO) value.getAnswer(), value.getPost(), context);
        }

        if (value.getAnswer().getType().equals(TypeSurvey.SURVEY_CHOICE)) {
            return isValidAnswerChoice((AnswerChoiceTO) value.getAnswer(), value.getPost(), context);
        }

        if (value.getAnswer().getType().equals(TypeSurvey.SURVEY_HIERARCHY)) {
            return isValidHierarchy((AnswerHierarchyTO) value.getAnswer(), value.getPost(), context);
        }

        if (value.getAnswer().getType().equals(TypeSurvey.SURVEY_RATE)) {
            return isValidRate((AnswerRateTO) value.getAnswer(), value.getPost(), context);
        }

        return just(false);
    }

    private Mono<Boolean> isValidRate(AnswerRateTO value, String id, FySelfContext context) {
        return postService.load(id, context)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(post ->
                        post.getContent().getTypeContent().equals(TypeContent.SURVEY_RATE) &&
                                (
                                        post.getAccess().equals(Access.PUBLIC) ||
                                                (
                                                        post.getAccess().equals(Access.PRIVATE) && post.getOwner().equals(context.getAccount().get().getId())
                                                )
                                )
                )
                .map(Boolean::booleanValue)
                .switchIfEmpty(just(false));
    }

    private Mono<Boolean> isValidHierarchy(AnswerHierarchyTO value, String id, FySelfContext context) {
        return postService.load(id, context)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(post ->
                        post.getContent().getTypeContent().equals(TypeContent.SURVEY_HIERARCHY) &&
                                (
                                        post.getAccess().equals(Access.PUBLIC) ||
                                                (
                                                        post.getAccess().equals(Access.PRIVATE) && post.getOwner().equals(context.getAccount().get().getId())
                                                )
                                )
                )
                .zipWith(postService.load(id, context)
                        .map(post -> ((HierarchySurveyTO) post.getContent())
                                .getOptions()
                                .stream()
                                .map(options -> value.getAnswer().containsKey(options.get("id"))))
                )
                .map(tuples -> tuples.getT1() && tuples.getT2().anyMatch(aBoolean -> true))
                .map(Boolean::booleanValue)
                .switchIfEmpty(just(false));
    }

    private Mono<Boolean> isValidAnswerChoice(AnswerChoiceTO value, String id, FySelfContext context) {
        return postService.load(id, context)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(post ->
                        post.getContent().getTypeContent().equals(TypeContent.SURVEY_CHOICE) &&
                                (
                                        post.getAccess().equals(Access.PUBLIC) ||
                                                (
                                                        post.getAccess().equals(Access.PRIVATE) && post.getOwner().equals(context.getAccount().get().getId())
                                                )
                                )
                )
                .zipWith(postService.load(id, context)
                        .map(post -> ((ChoiceSurveyTO) post.getContent())
                                .getChoices()
                                .stream()
                                .map(options -> options.keySet().retainAll(value.getAnswer()))
                        ))
                .map(tuples -> tuples.getT1() && tuples.getT2().anyMatch(aBoolean -> true))
                .map(Boolean::booleanValue)
                .switchIfEmpty(just(false));
    }

    private Mono<Boolean> isValidAnswerAsk(AnswerAskTO value, String id, FySelfContext context) {
        return postService.load(id, context)
                .switchIfEmpty(error(EntityNotFoundException::new))
                .map(post ->
                        post.getContent().getTypeContent().equals(TypeContent.SURVEY_ASK) &&
                                (
                                        post.getAccess().equals(Access.PUBLIC) ||
                                                (
                                                        post.getAccess().equals(Access.PRIVATE) && post.getOwner().equals(context.getAccount().get().getId())
                                                )
                                )
                )
                .map(Boolean::booleanValue)
                .switchIfEmpty(Mono.just(false));
    }
}
