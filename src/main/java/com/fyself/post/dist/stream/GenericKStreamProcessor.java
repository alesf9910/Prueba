package com.fyself.post.dist.stream;

import com.fyself.post.service.post.PostTimelineService;
import com.fyself.post.service.stream.StreamService;
import com.fyself.seedwork.kafka.reactive.ReactiveKafkaMessageQueue;
import com.fyself.seedwork.kafka.stereotype.Stream;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static com.fyself.post.service.post.contract.to.PostTimelineTO.from;
import static com.fyself.post.service.stream.contract.KafkaMessageBinder.KAFKA_MESSAGE_BINDER;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.just;

/**
 * Processor
 *
 * @author jmmarin
 * @since 0.1.0
 */
@Stream("stream")
public class GenericKStreamProcessor {

    private final PostTimelineService postTimelineService;
    private final StreamService streamService;


    public GenericKStreamProcessor(
            @Value("${mspost.application.kafka.topics.input.post}") String input_topic_post,
            ReactiveKafkaMessageQueue reactiveKafkaMessageQueue, PostTimelineService postTimelineService, StreamService streamService) throws Exception {
        this.postTimelineService = postTimelineService;
        this.streamService = streamService;

        reactiveKafkaMessageQueue.createSink(input_topic_post, this::createPostTimeline);
    }

    private Mono<Void> createPostTimeline(Map source) {
        return just(source)
                .filter(map -> source.containsKey("user") && source.containsKey("post") && source.containsKey("contacts"))
                .flatMapIterable(map -> (List<String>) source.get("contacts"))
                .flatMap(user -> postTimelineService.create(from(user, source.get("post").toString(), source.get("user").toString()))
                        .flatMap(ignored -> this.putInPipeline(user, source.get("post").toString())))
                .onErrorResume(throwable -> empty())
                .then();
    }

    private Mono<Void> putInPipeline(String user, String post) {
        return streamService.putInPipelinePostNotif(KAFKA_MESSAGE_BINDER.bindPostNotif(user, post));
    }
}
