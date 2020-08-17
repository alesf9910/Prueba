package com.fyself.post.service.post.contract.validation;
import com.fyself.post.service.post.contract.to.ContentTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.TextContentTO;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;
import com.fyself.post.tools.HexColorValidator;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;

@Aspect
@ValidatorInterceptor
public class PostCreateUpdateValidator extends MonoBiValidatorFixInterceptor<PostTO, FySelfContext> {


    @Around("execution(public * com.fyself.post.service.post.PostService+.create(..)) ||" +
            "execution(public * com.fyself.post.service.post.PostService+.update(..)) ")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "seed.error.rest.invalid_type_params");
    }


    @Override
    protected Mono<Boolean> validate(PostTO value, FySelfContext valueU) {
        ContentTO contentTO = value.getContent();
        if(value.getContent().getTypeContent().equals(TypeContent.TEXT)){
            TextContentTO textContentTO = (TextContentTO)contentTO;
            if(textContentTO.isBackground()){
                return Mono.just(HexColorValidator.validate(textContentTO.getBackgroundColor()));
            } else {
                return Mono.just(false);
            }
        } else {

        }
        return Mono.just(false);
    }
}
