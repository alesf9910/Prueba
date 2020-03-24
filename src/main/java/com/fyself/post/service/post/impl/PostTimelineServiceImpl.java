package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.PostTimelineService;
import com.fyself.post.service.post.contract.to.PostTimelineTO;
import com.fyself.post.service.post.datasource.PostTimelineRepository;
import com.fyself.seedwork.service.repository.mongodb.domain.DomainEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.fyself.post.service.post.contract.PostTimelineBinder.POST_TIMELINE_BINDER;

@Service("postTimelineService")
@Validated
public class PostTimelineServiceImpl implements PostTimelineService {
    private final PostTimelineRepository repository;

    public PostTimelineServiceImpl(PostTimelineRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<String> create(@NotNull @Valid PostTimelineTO to) {
        return repository.save(POST_TIMELINE_BINDER.bind(to)).map(DomainEntity::getId);
    }
}
