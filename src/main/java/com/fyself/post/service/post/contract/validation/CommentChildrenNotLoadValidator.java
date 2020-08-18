package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.datasource.CommentRepository;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.error.ErrorCode.INVALID_VALUE;
import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;
import static reactor.core.publisher.Mono.just;

/**
 * Check for comment validations
 *
 * @author jmmarin
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class CommentChildrenNotLoadValidator extends MonoBiValidatorFixInterceptor<String, FySelfContext> {

    final CommentRepository repository;

    public CommentChildrenNotLoadValidator(CommentRepository repository) {
        this.repository = repository;
        this.setCode(INVALID_VALUE);
    }

    @Around("execution(public * com.fyself.post.service.post.CommentService+.load(..))")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "fyself.service.post.comment.load.children");
    }

    @Override
    protected Mono<Boolean> validate(String value, FySelfContext context) {
        if (value == null || value.isBlank()) {
            return just(true);
        }

        if (context.getAccount().isEmpty()) {
            return just(false);
        }

        return repository.getById(value)
                .map(comment -> comment.getFather() == null)
                //check if i'm the post owner
                .zipWith(repository.getById(value).map(comment -> comment.getPost().getAccess().equals(Access.PUBLIC) || comment.getPost().getOwner().equals(context.getAccount().get().getId())))
                //evaluate the conditions
                .map(conditions -> conditions.getT1() || conditions.getT2())
                //if not satisfy any condition
                .switchIfEmpty(just(false))
                //if error occur
                .onErrorResume(throwable -> just(false));
    }
}
