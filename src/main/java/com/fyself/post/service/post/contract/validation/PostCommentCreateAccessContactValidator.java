package com.fyself.post.service.post.contract.validation;

import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor;
import com.fyself.seedwork.service.validation.stereotype.ValidatorInterceptor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import static com.fyself.seedwork.error.ErrorCode.FORBIDDEN_ACCESS;
import static com.fyself.seedwork.service.validation.MonoBiValidatorFixInterceptor.Position.LAST;
import static reactor.core.publisher.Mono.just;

/**
 * Check for report validations
 *
 * @author Alejandro
 * @since 0.0.1
 */
@Aspect
@ValidatorInterceptor
public class PostCommentCreateAccessContactValidator extends MonoBiValidatorFixInterceptor<CommentTO, FySelfContext> {

    private final PostRepository postRepository;

    public PostCommentCreateAccessContactValidator(PostRepository postRepository) {
        this.postRepository = postRepository;
        this.setCode(FORBIDDEN_ACCESS);
    }

    @Around(
            "execution(public * com.fyself.post.service.post.CommentService+.add(..))"
    )
    public Object intercept(ProceedingJoinPoint procedure) {
        return this.proceed(procedure, 0, LAST, "seed.security.unauthorized");
    }

    @Override
    protected Mono<Boolean> validate(CommentTO value, FySelfContext context) {

        if (context.getAccount().isEmpty()) {
            return just(false);
        }

        if (value.getPost() == null || value.getPost().isEmpty()) {
            return just(true);
        }

        return postRepository.getById(value.getPost())
                .flatMap(post ->
                        //Check if PUBLIC
                        just(post.getAccess().equals(Access.PUBLIC))
                                //Check if PRIVATE must to be shared with me or i am the owner od the post
                                .zipWith(
                                        //Check if i'm the post owner
                                        just(post.getOwner().equals(context.getAccount().get().getId()) ||
                                                //Check if is private and shared with me
                                                (post.getAccess().equals(Access.PRIVATE) && post.getSharedWith().contains(context.getAccount().get().getId())))
                                ))
                //Evaluate the conditions
                .map(this::evaluate)
                .switchIfEmpty(just(false))
                //if error occur
                .onErrorResume(throwable -> just(false));
    }

    private <R> Boolean evaluate(Tuple2<Boolean, Boolean> values) {
        return values.getT1() || values.getT2();
    }
}
