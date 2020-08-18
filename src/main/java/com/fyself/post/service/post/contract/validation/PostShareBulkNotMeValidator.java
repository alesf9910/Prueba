package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.PostShareBulkTO;
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
 * Check for share post validations
 *
 * @author jmmarin
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class PostShareBulkNotMeValidator extends MonoBiValidatorFixInterceptor<PostShareBulkTO, FySelfContext> {

    final PostRepository repository;

    public PostShareBulkNotMeValidator(PostRepository repository) {
        this.repository = repository;
        this.setCode(INVALID_VALUE);
    }

    @Around("execution(public * com.fyself.post.service.post.PostService+.shareBulk(..))")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "fyself.service.post.share.not.me");
    }

    @Override
    protected Mono<Boolean> validate(PostShareBulkTO to, FySelfContext context) {
        if (to == null)
            return just(true);
        if (to.getSharedWith().isEmpty())
            return just(true);
        return repository.getById(to.getPost())
                .map(post -> !to.getSharedWith().stream().anyMatch(s -> post.getOwner().equals(s) ))
                .switchIfEmpty(just(true));
    }
}
