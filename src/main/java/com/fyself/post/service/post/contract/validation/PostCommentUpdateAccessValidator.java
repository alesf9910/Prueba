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

import static com.fyself.post.tools.Roles.POST_ADMIN;
import static com.fyself.seedwork.error.ErrorCode.FORBIDDEN_ACCESS;
import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;

/**
 * Check for report validations
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class PostCommentUpdateAccessValidator extends MonoBiValidatorFixInterceptor<CommentTO, FySelfContext> {

    final CommentRepository repository;

    public PostCommentUpdateAccessValidator(CommentRepository repository) {
        this.repository = repository;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around(
            "execution(public * com.fyself.post.service.post.CommentService+.update(..))"
    )
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "seed.security.unauthorized");
    }

    @Override
    protected Mono<Boolean> validate(CommentTO value, FySelfContext context) {

        if (context.getAccount().isEmpty()) {
            return Mono.just(false);
        }

        return repository.getById(value.getId())
                .map(comment ->
                        comment.getOwner().equals(context.getAccount().get().getId())
                        || context.getAccount().get().getRoles().contains(POST_ADMIN))
                .switchIfEmpty(Mono.just(false));
    }
}
