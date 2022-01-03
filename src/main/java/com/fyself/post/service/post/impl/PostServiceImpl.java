package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.contract.to.PostShareBulkTO;
import com.fyself.post.service.post.contract.to.PostShareTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.SharedPostTO;
import com.fyself.post.service.post.contract.to.criteria.PostCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.post.service.post.datasource.AnswerSurveyRepository;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.post.service.system.datasource.UserRepository;
import com.fyself.post.service.post.datasource.PostTimelineRepository;
import com.fyself.post.service.post.datasource.domain.Post;
import com.fyself.post.service.post.datasource.domain.enums.TypeContent;
import com.fyself.post.service.post.datasource.domain.subentities.SharedPost;
import com.fyself.post.service.stream.StreamService;
import com.fyself.post.tools.enums.Access;
import com.fyself.seedwork.service.EntityNotFoundException;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.ValidationException;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

import static com.fyself.post.service.post.contract.AnswerSurveyBinder.ANSWER_SURVEY_BINDER;
import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.service.post.contract.PostTimelineBinder.POST_TIMELINE_BINDER;
import static com.fyself.post.tools.LoggerUtils.*;
import static reactor.core.publisher.Flux.fromIterable;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

@Service("postService")
@Validated
public class PostServiceImpl implements PostService {

  final PostRepository repository;
  final UserRepository userRepository;
  final PostTimelineRepository postTimelineRepository;
  final AnswerSurveyRepository answerSurveyRepository;
  final StreamService streamService;
  PostTO postTOAux;

  public PostServiceImpl(PostRepository repository, UserRepository userRepository, PostTimelineRepository postTimelineRepository,
      AnswerSurveyRepository answerSurveyRepository, StreamService streamService) {
    this.repository = repository;
    this.postTimelineRepository = postTimelineRepository;
    this.answerSurveyRepository = answerSurveyRepository;
    this.streamService = streamService;
    this.userRepository = userRepository;
  }

  @Override
  public Mono<String> create(@NotNull @Valid PostTO to, FySelfContext context) {
    return context.authenticatedId()
        .flatMap(userId -> createPost(
            POST_BINDER.bind(to.withUserId(userId).withCreatedAt().withUpdatedAt()), context))
        .switchIfEmpty(error(EntityNotFoundException::new))
        .map(DomainEntity::getId);
  }

  @Override
  public Mono<String> createPostWorkspace(@NotNull @Valid PostTO to, FySelfContext context) {
    return context.authenticatedId()
            .flatMap(userId -> createPostWS(
                    POST_BINDER.bind(to.withUserId(userId).withCreatedAt().withUpdatedAt()), context))
            .switchIfEmpty(error(EntityNotFoundException::new))
            .map(DomainEntity::getId);
  }

  @Override
  public Mono<Void> update(@NotNull @Valid PostTO to, FySelfContext context) {
    return repository.getById(to.getId())
        .map(post -> POST_BINDER.set(post, to.withUpdatedAt()))
        .flatMap(post -> repository.save(post)
            .doOnSuccess(entity -> updateEvent(entity, context))
            .doOnSuccess(
                entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity))
                    .subscribe())
        )
        .switchIfEmpty(error(EntityNotFoundException::new))
        .then();
  }

  @Override
  public Mono<PostTO> load(@NotNull String id, FySelfContext context) {
    return repository.getById(id)
        .flatMap(post -> loadPost(post, context))
        .switchIfEmpty(error(EntityNotFoundException::new));
  }

  /**
   * This method delete user post, contact user posttimeline and user, contact post notification
   *
   * @param  id  id of post
   * @param  context context of FySelf with user information, tokend security ...
   */
  @Override
  public Mono<Void> delete(@NotNull String id, FySelfContext context) {
    return repository.getById(id)
        .switchIfEmpty(error(EntityNotFoundException::new))
        .flatMap(post -> repository.softDelete(post)
            .doOnSuccess(entity -> deleteEvent(post, context))
            .doOnSuccess(
                entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity))
                    .subscribe())
            .doOnSuccess(entity -> postTimelineRepository
                .deleteAllByPost_IdAndUserOrOwner(entity.getId(), entity.getOwner(), entity.getOwner()).subscribe())
            .doOnSuccess(
                        entity -> streamService.putInPipelineDeletePostNotification(Map.of("type","DELETE-POST-NOT","post",entity.getId()))
                                .subscribe())
        )
        .then();
  }

  @Override
  public Mono<Void> deleteByEnterprise(@NotNull String id, FySelfContext context){
    return repository.findAllByEnterprise(id)
            .switchIfEmpty(error(EntityNotFoundException::new))
            .flatMap(post -> repository.softDelete(post)
                    .doOnSuccess(entity -> deleteEvent(post, context))
                    .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)).subscribe())
                    .doOnSuccess(entity -> postTimelineRepository.deleteAllByPost_IdAndUserOrOwner(entity.getId(), entity.getOwner(), entity.getOwner()).subscribe())
                    .doOnSuccess(entity -> streamService.putInPipelineDeletePostNotification(Map.of("type","DELETE-POST-NOT","post",entity.getId())).subscribe())
            )
            .then();
  }

  @Override
  public Mono<Post> patch(@NotNull String id, HashMap to, FySelfContext context) {
    if (!to.containsKey("pinned")) {
      return error(EntityNotFoundException::new);
    }

    boolean v = false;
    try {
      v = (Boolean) to.get("pinned");
    } catch (Exception e) {
    }

    boolean finalV = v;
    return repository.getById(id)
        .switchIfEmpty(error(EntityNotFoundException::new))
        .flatMap(post -> repository.save(post.putPinned(finalV)));
  }

  @Override
  public Mono<PagedList<PostTO>> search(@NotNull PostCriteriaTO criteria, FySelfContext context) {
    return repository.findPage(
        POST_BINDER.bindToCriteria(criteria.withOwner(context.getAccount().get().getId())))
        .map(POST_BINDER::bindPage)
        .flatMap(postTOPagedList -> fromIterable(postTOPagedList.getElements())
            .flatMap(
                postTO ->
                    answerSurveyRepository
                        .findByPostAndUser(
                            postTO.getContent() != null ?
                                postTO.getContent().getTypeContent() != null ?
                                    postTO.getContent().getTypeContent() == TypeContent.SHARED_POST?
                                        ((SharedPostTO) postTO.getContent()).getPostTo().getId() :
                                        postTO.getId()
                                    : postTO.getId()
                                : postTO.getId()
                            , context.getAccount().get().getId()
                        )
                        .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
                        .map(answerSurveyTO -> POST_BINDER
                            .bindPostTOWithAnswer(postTO, answerSurveyTO))
                        .switchIfEmpty(just(postTO)), 1)
            .collectList()
            .map(postTOS -> POST_BINDER.bind(postTOPagedList, postTOS)));
  }

  @Override
  public Mono<PagedList<PostTO>> searchByEnterprise(@NotNull PostCriteriaTO criteria, FySelfContext context) {
    return repository.findPage(
            POST_BINDER.bindToCriteria(criteria.putWorkspace(true).putEnterprise(context.getAccount().get().getBusiness())))
            .map(POST_BINDER::bindPage)
            .flatMap(postTOPagedList -> fromIterable(postTOPagedList.getElements())
                    .flatMap(
                            postTO ->
                                    answerSurveyRepository
                                            .findByPostAndUser(
                                                    postTO.getContent() != null ?
                                                            postTO.getContent().getTypeContent() != null ?
                                                                    postTO.getContent().getTypeContent() == TypeContent.SHARED_POST?
                                                                            ((SharedPostTO) postTO.getContent()).getPostTo().getId() :
                                                                            postTO.getId()
                                                                    : postTO.getId()
                                                            : postTO.getId()
                                                    , context.getAccount().get().getId()
                                            )
                                            .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
                                            .map(answerSurveyTO -> POST_BINDER
                                                    .bindPostTOWithAnswer(postTO, answerSurveyTO))
                                            .switchIfEmpty(just(postTO)), 1)
                    .collectList()
                    .map(postTOS -> POST_BINDER.bind(postTOPagedList, postTOS)));
  }

  private Mono<PostTO> loadPost(Post postTO, FySelfContext context) {
    return answerSurveyRepository.findByPostAndUser(


        postTO.getContent() != null ?
            postTO.getContent().getTypeContent() != null ?
                postTO.getContent().getTypeContent() == TypeContent.SHARED_POST ?
                    ((SharedPost) postTO.getContent()).getPost()
                        : postTO.getId()
                  : postTO.getId()
            : postTO.getId()
        ,

        context.getAccount().get().getId()

    )
        .map(ANSWER_SURVEY_BINDER::bindFromSurvey)
        .map(answerSurveyTO -> POST_BINDER.bindPostWithAnswer(postTO, answerSurveyTO))
        .switchIfEmpty(just(POST_BINDER.bind(postTO)));
  }

  @Override
  public Mono<PostTO> addSharedPostContent(PostTO postTO) {
    return repository.getById(((SharedPostTO) postTO.getContent()).getPostTo().getId())
        .map(post -> postTO.withSharedContent(POST_BINDER.bind(post)));
  }

  @Override
  public Mono<Void> block(String post) {
    return repository.findById(post)
        .map(POST_BINDER::bindBlocked)
        .flatMap(repository::save)
        .doOnSuccess(
            entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity)))
        .then();
  }

  @Override
  public Mono<Void> shareWith(@NotNull PostShareTO to, FySelfContext context) {
    return repository.findById(to.getPost())
        .flatMap(post -> repository.save(POST_BINDER.bindShareWith(post, to)))
        .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity))
            .subscribe())
        .switchIfEmpty(error(EntityNotFoundException::new))
        .then();
  }

  @Override
  public Mono<String> sharePost(@NotNull PostShareBulkTO to, FySelfContext context) {
    return repository.findById(to.getPost())
        .flatMap(post -> {
          if(post.getWorkspace())
          {
             if(to.getSharedWith().isEmpty())
             {
               return userRepository.loadUsersWorkspace(post.getEnterprise(),context)
                       .flatMap(users ->
                               shareBulk(POST_BINDER.bindShareBulk(post, to.putSharedWith(users)), context)
                                       .filter(Boolean::booleanValue).map(ing -> to.getPost())
                       );
             }
             else
             {
               return shareBulk(POST_BINDER.bindShareBulk(post, to), context)
                      .filter(Boolean::booleanValue).map(ing -> to.getPost());
             }

          }
          else
          {
            if (post.getOwner().equals(context.getAccount().get().getId())) {
              return shareBulk(POST_BINDER.bindShareBulk(post, to), context)
                      .filter(Boolean::booleanValue).map(ing -> to.getPost());
            } else {
              if (post.getAccess().equals(Access.PUBLIC)) {
                return this.checkPostContent(post).flatMap(checkingContent -> {
                  if (checkingContent) {
                    return createPost(
                            POST_BINDER.bindSharedPost(post, context.getAccount().get().getId()), context)
                            .map(DomainEntity::getId);
                  } else {
                    return createPost(
                            POST_BINDER.bindReSharedPost(post, context.getAccount().get().getId()), context)
                            .map(DomainEntity::getId);
                  }
                });
              } else {
                return error(ValidationException::new);
              }
            }
          }
        })
        .switchIfEmpty(error(EntityNotFoundException::new));
  }

  private Mono<Boolean> checkPostContent(Post post) {
    if (post.getContent().getTypeContent() != null)
      return just(true);
    else
      return just(false);
  }

  @Override
  public Mono<Void> stopShareWith(@NotNull PostShareTO to, FySelfContext context) {
    return repository.findById(to.getPost())
        .flatMap(post -> repository.save(POST_BINDER.bindStopShareWith(post, to)))
        .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity))
            .subscribe())
        .switchIfEmpty(error(EntityNotFoundException::new))
        .then();
  }

  @Override
  public Mono<PagedList<PostTO>> searchMe(PostTimelineCriteriaTO criteria, FySelfContext context) {
    return repository
        .findPage(POST_BINDER.bindToCriteria(criteria.withUser(context.getAccount().get().getId())))
        .map(POST_BINDER::bindPage);
  }

  @Override
  public Mono<Void> create(PostTO to) {
    return repository
        .save(POST_BINDER.bind(to.withUserId(to.getOwner()).withCreatedAt().withUpdatedAt()))
        .flatMap(
            post -> postTimelineRepository.save(POST_TIMELINE_BINDER.bind(post)).thenReturn(post))
        .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity))
            .subscribe())
        .doOnSuccess(entity -> createEvent(entity, to.getOwner()))
        .switchIfEmpty(error(EntityNotFoundException::new))
        .then();
  }

  @Override
  public Mono<Void> unpinnedPost(String userId) {
    return repository.findAllByOwnerPinned(userId)
        .flatMap(post -> repository.save(POST_BINDER.setUnpinned(post))).then();
  }

  private Mono<Boolean> shareBulk(@NotNull Post to, FySelfContext context) {
    return repository.save(to)
            .flatMap( post -> this.saveInPostTimeLineRepository(post))
        .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity))
            .subscribe())
        .then(just(true));
  }

  private Mono<Post> saveInPostTimeLineRepository(Post post) {
    for (String sharedWi : post.getSharedWith()) {
      postTimelineRepository.findAllByUserAndPost(sharedWi, post.getId()).map(count -> {
        if (count == 0)
          postTimelineRepository.save(POST_TIMELINE_BINDER.bindU(post, sharedWi)).subscribe();
        return count;
      }).subscribe();
    }
    return just(post);
  }

  private Mono<Post> createPost(@NotNull Post to, FySelfContext context) {
    return repository.save(to)
        .flatMap(
            post -> postTimelineRepository.save(POST_TIMELINE_BINDER.bind(post)).thenReturn(post))
        .doOnSuccess(entity -> createEvent(entity, context))
        .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity))
            .subscribe());
  }

  private Mono<Post> createPostWS(@NotNull Post to, FySelfContext context) {
    return repository.save(to)
            .flatMap(
                    post -> postTimelineRepository.save(POST_TIMELINE_BINDER.bind(post)).thenReturn(post))
            .doOnSuccess(entity -> createEventWS(entity, context, to.getEnterprise()))
            .doOnSuccess(entity -> streamService.putInPipelinePostElastic(POST_BINDER.bindIndex(entity))
                    .subscribe());
  }
}
