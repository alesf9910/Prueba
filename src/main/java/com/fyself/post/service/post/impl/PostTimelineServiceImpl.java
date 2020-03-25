package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostTimelineService;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.post.service.post.datasource.PostRepository;
import com.fyself.post.service.post.datasource.PostTimelineRepository;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.fyself.post.service.post.contract.PostBinder.POST_BINDER;
import static com.fyself.post.service.post.contract.PostTimelineBinder.POST_TIMELINE_BINDER;

@Service("postTimelineService")
@Validated
public class PostTimelineServiceImpl implements PostTimelineService {
    private final PostTimelineRepository repository;
    private final PostRepository postRepository;

    public PostTimelineServiceImpl(PostTimelineRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    @Override
    public Mono<String> create(@NotNull @Valid PostTimelineTO to) {
        return repository.save(POST_TIMELINE_BINDER.bind(to)).map(DomainEntity::getId);
    }

    @Override
    public Mono<PagedList<PostTO>> search(FySelfContext context) {
        return context.authenticatedId()
                .flatMapMany(repository::findAllByUser)
                .map(postTimeline -> postTimeline.getPost())
                .collectList()
                .map(POST_BINDER::bindList);
    }
}
