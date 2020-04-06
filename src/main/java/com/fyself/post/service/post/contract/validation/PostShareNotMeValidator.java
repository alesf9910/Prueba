package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.PostShareTO;
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

/**
 * Check for report validations
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class PostShareNotMeValidator extends MonoBiValidatorFixInterceptor<PostShareTO, FySelfContext> {

    final PostRepository repository;

    public PostShareNotMeValidator(PostRepository repository) {
        this.repository = repository;
        this.setCode(INVALID_VALUE);
    }

    @Around("execution(public * com.fyself.post.service.post.PostService+.shareWith(..))")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "fyself.service.post.share.not.me");
    }

    @Override
    protected Mono<Boolean> validate(PostShareTO value, FySelfContext context) {

        if (context.getAccount().isEmpty()) {
            return Mono.just(false);
        }

        return repository.findById(value.getPost())
                .map(post -> !post.getOwner().equals(value.getSharedWith()));
    }
}
