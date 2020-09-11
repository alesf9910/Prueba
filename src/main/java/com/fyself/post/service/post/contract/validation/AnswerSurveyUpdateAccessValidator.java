package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.PostService;
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
import static reactor.core.publisher.Mono.just;

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
    final PostService postService;

    public AnswerSurveyUpdateAccessValidator(AnswerSurveyRepository repository, PostService postService) {
        this.repository = repository;
        this.postService = postService;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around(
            "execution(public * com.fyself.post.service.post.AnswerSurveyService+.update(..))"
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

        return new AnswerSurveyValidator(repository, postService, value, context,
            repositoryTimeline).isValidAnswer();
    }
}
