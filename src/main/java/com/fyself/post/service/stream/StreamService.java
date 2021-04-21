package com.fyself.post.service.stream;

import reactor.core.publisher.Mono;

import java.util.Map;

public interface StreamService {
    Mono<Void> putInPipelineAnswerElastic(Map message);
    Mono<Void> putInPipelinePostElastic(Map message);
    Mono<Void> putInPipelineDeletePostNotification(Map message);
    Mono<Void> putInPipelinePostNotification(Map message);
}
