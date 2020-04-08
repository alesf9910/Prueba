package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.PostShareBulkTO;
import com.fyself.post.service.system.datasource.UserRepository;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.fyself.seedwork.error.ErrorCode.INVALID_VALUE;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.just;


/**
 * Check that the user id is correct.
 *
 * @author jmmarin
 * @since 0.1.0
 */
@Aspect
@ValidatorInterceptor
public class PostSharedBulkUserExistAccessValidator extends MonoBiValidatorFixInterceptor<PostShareBulkTO, FySelfContext> {

    private final UserRepository repository;

    public PostSharedBulkUserExistAccessValidator(UserRepository repository) {
        this.repository = repository;
        this.setCode(INVALID_VALUE);
    }

    @Around("execution(public * com.fyself.post.service.post.PostService+.shareBulk(..))")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, Position.LAST, "fyself.service.post.shared.valid.user");
    }

    @Override
    public Mono<Boolean> validate(PostShareBulkTO to, FySelfContext context) {
        if (to == null)
            return just(true);
        if (to.getSharedWith().isEmpty())
            return just(true);
        return just(to)
                .flatMap(postShareBulkTO -> fromIterable(postShareBulkTO.getSharedWith())
                        .flatMap(user -> repository.existUser(user, context))
                        .filter(aBoolean -> !aBoolean)
                        .collectList())
                .map(List::isEmpty);
    }
}
