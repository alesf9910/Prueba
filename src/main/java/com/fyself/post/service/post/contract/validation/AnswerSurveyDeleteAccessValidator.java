package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.error.ErrorCode.FORBIDDEN_ACCESS;
import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;

/**
 * Check for answer delete, if the user is not the owner of the answer then return a 403 error
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class AnswerSurveyDeleteAccessValidator extends MonoBiValidatorFixInterceptor<String, FySelfContext> {

    final AnswerSurveyRepository repository;

    public AnswerSurveyDeleteAccessValidator(AnswerSurveyRepository repository) {
        this.repository = repository;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around(
            "execution(public * com.fyself.post.service.post.AnswerSurveyService+.delete(..))"
    )
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "seed.security.unauthorized");
    }

    @Override
    protected Mono<Boolean> validate(String value, FySelfContext context) {
        if (value == null) {
            return Mono.just(true);
        }

        return context.authenticatedId()
                .flatMap(
                        userId -> repository.getById(value)
                                .map(
                                        survey -> survey.getOwner().equals(userId)
                                )
                )
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.just(false));
    }
}
