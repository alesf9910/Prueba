package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.datasource.CommentRepository;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.error.ErrorCode.INVALID_VALUE;
import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;

/**
 * Check for comment validations
 *
 * @author jmmarin
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class CommentNotChildrenValidator extends MonoBiValidatorFixInterceptor<CommentTO, FySelfContext> {

    final CommentRepository repository;

    public CommentNotChildrenValidator(CommentRepository repository) {
        this.repository = repository;
        this.setCode(INVALID_VALUE);
    }

    @Around("execution(public * com.fyself.post.service.post.CommentService+.add(..))")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "fyself.service.post.comment.add.children");
    }

    @Override
    protected Mono<Boolean> validate(CommentTO value, FySelfContext context) {
        if (value == null) {
            return Mono.just(true);
        }
        if (value.getFather() == null) {
            return Mono.just(true);
        }
        return repository.getById(value.getFather())
                .map(comment -> comment.getFather() == null);
    }
}
