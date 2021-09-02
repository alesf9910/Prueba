package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.ContentTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.TextContentTO;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;
import com.fyself.post.service.system.datasource.EnterpriseRepository;
import com.fyself.post.tools.HexColorValidator;
import com.fyself.seedwork.error.ErrorCode;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;

import static com.fyself.seedwork.error.ErrorCode.INVALID_INPUT;
import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;

@Aspect
@ValidatorInterceptor
public class PostCreateUpdateWorkspaceValidator extends MonoBiValidatorFixInterceptor<PostTO, FySelfContext> {

    private final EnterpriseRepository enterpriseRepository;
    private String message = "fyself.service.post.create.post.enterprise.exist";

    public PostCreateUpdateWorkspaceValidator(EnterpriseRepository enterpriseRepository) {
        this.enterpriseRepository = enterpriseRepository;
        this.setCode(INVALID_INPUT);
    }

    @Around("execution(public * com.fyself.post.service.post.PostService+.createPostWorkspace(..)) ||" +
            "execution(public * com.fyself.post.service.post.PostService+.update(..)) ")
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, message);
    }

    @Override
    protected Mono<Boolean> validate(PostTO value, FySelfContext context) {
        return Mono.zip(validatePost(value), validateEnterpriseEliminated(value, context), ((post, enterpriseExist) -> {
            if (enterpriseExist){
                return false;
            }else{
                message = "fyself.service.post.enterprise.validate";
                return post;
            }
        }));
    }

    private Mono<Boolean> validateEnterpriseEliminated(PostTO value, FySelfContext context){
        return enterpriseRepository.getEnterprise(value.getEnterprise(), context);
    }

    private Mono<Boolean> validatePost(PostTO value){
        ContentTO contentTO = value.getContent();
        if(value.getContent().getTypeContent().equals(TypeContent.TEXT)){
            TextContentTO textContentTO = (TextContentTO)contentTO;
            if(textContentTO.getBackgroundColor()!=null){
                return Mono.just(HexColorValidator.validate(textContentTO.getBackgroundColor()));
            }
        }
        return Mono.just(true);
    }
}
