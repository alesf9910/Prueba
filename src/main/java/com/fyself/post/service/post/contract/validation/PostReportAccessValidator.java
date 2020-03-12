package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.datasource.PostReportRepository;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

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
public class PostReportAccessValidator extends MonoBiValidatorFixInterceptor<PostReportTO, FySelfContext> {

    final PostReportRepository repository;

    public PostReportAccessValidator(PostReportRepository repository) {
        this.repository = repository;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around(
            "execution(public * com.fyself.post.service.post.PostReportService+.add(..))"
    )
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "fyself.service.post.report.with.me");
    }

    @Override
    protected Mono<Boolean> validate(PostReportTO value, FySelfContext context) {

        if (value.getUser() == null || value.getUser().trim().equals("")) {
            return Mono.just(true);
        }

        if (context.getAccount().isEmpty()) {
            return Mono.just(false);
        }

        return Mono.just(!value.getUser().equals(context.getAccount().get().getId()));
    }
}
