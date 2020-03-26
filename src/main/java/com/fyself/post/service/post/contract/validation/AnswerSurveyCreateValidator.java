package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.*;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.domain.enums.TypeSurvey;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerHierarchy;
import com.fyself.post.service.post.datasource.domain.subentities.AnswerRate;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.error.ErrorCode.INVALID_FORMAT;
import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;

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

    public AnswerSurveyCreateValidator(AnswerSurveyRepository repository) {
        this.repository = repository;
        this.setCode(INVALID_FORMAT);
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
            return Mono.just(false);
        }

        if (value.getPost() == null) {
            return Mono.just(false);
        }

        return Mono.just(isValidAnswer(value));
    }

    private boolean isValidAnswer(AnswerSurveyTO value) {
        if (value.getAnswer().getType().equals(TypeSurvey.ASK)) {
            return isValidAnswerAsk((AnswerAskTO) value.getAnswer());
        }

        if (value.getAnswer().getType().equals(TypeSurvey.CHOICE)) {
            return isValidAnswerChoice((AnswerChoiceTO) value.getAnswer());
        }

        if (value.getAnswer().getType().equals(TypeSurvey.HIERARCHY)) {
            return isValidHierarchy((AnswerHierarchyTO) value.getAnswer());
        }

        if (value.getAnswer().getType().equals(TypeSurvey.RATE)) {
            return isValidRate((AnswerRateTO) value.getAnswer());
        }

        return false;
    }

    private boolean isValidRate(AnswerRateTO value) {
        return value.getAnswer() != null && !(value.getAnswer() < 0);
    }

    private boolean isValidHierarchy(AnswerHierarchyTO value) {
        return value.getAnswer() != null && !value.getAnswer().isEmpty();
    }

    private boolean isValidAnswerChoice(AnswerChoiceTO value) {
        return value.getAnswer() != null && !value.getAnswer().isEmpty();
    }

    private boolean isValidAnswerAsk(AnswerAskTO value) {
        return value.getAnswer() != null && !value.getAnswer().trim().equals("");
    }
}
