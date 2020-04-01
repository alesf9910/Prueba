package com.fyself.post.service.stream;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface StreamService {
    Mono<Void> putInPipelineAnswerElastic(Map message);
    Mono<Void> putInPipelinePostNotif(Map message);
}
