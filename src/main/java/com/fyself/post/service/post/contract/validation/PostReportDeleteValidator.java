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
public class PostReportDeleteValidator extends MonoBiValidatorFixInterceptor<String, FySelfContext> {

    final PostReportRepository repository;

    public PostReportDeleteValidator(PostReportRepository repository) {
        this.repository = repository;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around(
            "execution(public * com.fyself.post.service.post.PostReportService+.delete(..))"
    )
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "fyself.service.post.report.delete");
    }

    @Override
    protected Mono<Boolean> validate(String value, FySelfContext context) {
        return repository.getById(value)
                .map(postReport -> postReport.getOwner().equals(context.getAccount().get().getId()))
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.just(false));
    }
}
