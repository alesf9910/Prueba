package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.post.service.post.datasource.domain.Post;
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
import static reactor.core.publisher.Mono.just;

/**
 * Check for Post locked validations
 *
 * @author jmmarin
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class PostDeleteLockedValidator extends MonoBiValidatorFixInterceptor<String, FySelfContext> {

    final PostRepository repository;

    public PostDeleteLockedValidator(PostRepository repository) {
        this.repository = repository;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around("execution(public * com.fyself.post.service.post.PostService+.delete(..))")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "fyself.service.post.delete.locked");
    }

    @Override
    protected Mono<Boolean> validate(String value, FySelfContext context) {
        if (value == null) {
            return just(true);
        }
        if (value.isBlank())
            return just(true);
        return repository.getById(value)
                .map(Post::isBlocked)
                .filter(locked -> locked)
                .map(ignored -> context.getAccount().get().getRoles().contains(POST_ADMIN))
                .switchIfEmpty(just(true));
    }
}
