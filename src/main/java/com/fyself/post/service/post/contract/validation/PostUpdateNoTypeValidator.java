package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.datasource.PostRepository;
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
 * Check for Post type validations
 *
 * @author jmmarin
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class PostUpdateNoTypeValidator extends MonoBiValidatorFixInterceptor<PostTO, FySelfContext> {

    final PostRepository repository;

    public PostUpdateNoTypeValidator(PostRepository repository) {
        this.repository = repository;
        this.setCode(INVALID_VALUE);
    }

    @Around("execution(public * com.fyself.post.service.post.PostService+.update(..))")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "fyself.service.post.update.type");
    }

    @Override
    protected Mono<Boolean> validate(PostTO value, FySelfContext context) {
        if (value == null)
            return just(true);

        if (value.getContent().getTypeContent().toString().isBlank())
            return just(true);

        return repository.getById(value.getId())
                .map(post -> post.getContent().getTypeContent().equals(value.getContent().getTypeContent()))
                .onErrorResume(throwable -> just(true))
                .switchIfEmpty(just(true));
    }
}
