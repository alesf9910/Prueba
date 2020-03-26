package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.AnswerSurveyTO;
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
 * Check for update answer, if the user is not the owner or try to update a answer with different type then return a 403 error
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class AnswerSurveyUpdateAccessValidator extends MonoBiValidatorFixInterceptor<AnswerSurveyTO, FySelfContext> {

    final AnswerSurveyRepository repository;

    public AnswerSurveyUpdateAccessValidator(AnswerSurveyRepository repository) {
        this.repository = repository;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around(
            "execution(public * com.fyself.post.service.post.AnswerSurveyService+.update(..))"
    )
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "seed.security.unauthorized");
    }

    @Override
    protected Mono<Boolean> validate(AnswerSurveyTO value, FySelfContext context) {
        if (value.getAnswer() == null) {
            return Mono.just(true);
        }

        if (value.getPost() == null) {
            return Mono.just(true);
        }

        if (value.getId() == null) {
            return Mono.just(false);
        }

        return context.authenticatedId()
                .flatMap(
                        userId -> repository.getById(value.getId())
                                .map(
                                        survey -> survey.getOwner().equals(userId)
                                )
                )
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.just(false))
                .flatMap(
                        aBoolean -> {
                            return repository.getById(value.getId())
                                    .map(
                                            survey -> survey.getAnswer().getType().equals(value.getAnswer().getType())
                                    );
                        }
                ).filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.just(false));
    }
}
