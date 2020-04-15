package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.datasource.CommentRepository;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import static com.fyself.post.tools.enums.Access.PUBLIC;
import static com.fyself.seedwork.error.ErrorCode.FORBIDDEN_ACCESS;
import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;
import static reactor.core.publisher.Mono.just;

/**
 * Check for report validations
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class PostCommentGetAccessValidator extends MonoBiValidatorFixInterceptor<String, FySelfContext> {

    final CommentRepository repository;

    public PostCommentGetAccessValidator(CommentRepository repository) {
        this.repository = repository;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around(
            "execution(public * com.fyself.post.service.post.CommentService+.load(..))"
    )
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "seed.security.unauthorized");
    }

    @Override
    protected Mono<Boolean> validate(String value, FySelfContext context) {

        if (value == null) {
            return just(true);
        }

        if (value.trim().equals("")) {
            return just(true);
        }

        return repository.getById(value)
                .map(comment -> comment.getPost().getOwner().equals(context.getAccount().get().getId()) || (comment.getPost().getAccess().equals(PUBLIC) && !comment.getPost().isBlocked()))
                .onErrorResume(throwable -> just(false))
                .switchIfEmpty(just(false));
    }
}
