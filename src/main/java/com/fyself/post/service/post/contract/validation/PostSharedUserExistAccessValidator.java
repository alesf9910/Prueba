package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.PostShareTO;
import com.fyself.post.service.system.datasource.UserRepository;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.error.ErrorCode.FORBIDDEN_ACCESS;
import static com.fyself.seedwork.error.ErrorCode.INVALID_VALUE;


/**
 * Check that the user id is correct.
 *
 * @author jmmarin
 * @since 0.1.0
 */
@Aspect
@ValidatorInterceptor
public class PostSharedUserExistAccessValidator extends MonoBiValidatorFixInterceptor<PostShareTO, FySelfContext> {

    private final UserRepository repository;

    public PostSharedUserExistAccessValidator(UserRepository repository) {
        this.repository = repository;
        this.setCode(INVALID_VALUE);
    }

    @Around("execution(public * com.fyself.post.service.post.PostService+.shareWith(..))")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, Position.LAST, "fyself.service.post.shared.valid.user");
    }

    @Override
    public Mono<Boolean> validate(PostShareTO to, FySelfContext context) {
        return repository.existUser(to.getSharedWith(), context);
    }
}
