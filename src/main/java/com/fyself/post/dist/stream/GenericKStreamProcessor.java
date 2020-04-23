package com.fyself.post.dist.stream;

import com.fyself.post.service.post.PostTimelineService;
import com.fyself.seedwork.kafka.reactive.ReactiveKafkaMessageQueue;
import com.fyself.seedwork.kafka.stereotype.Stream;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

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
    private final String output_topic_notification;


    public GenericKStreamProcessor(
            @Value("${mspost.application.kafka.topics.input.post}") String input_topic_post,
            @Value("${mspost.application.kafka.topics.output.notification-socket}") String output_topic_notification,
            ReactiveKafkaMessageQueue reactiveKafkaMessageQueue, PostTimelineService postTimelineService) throws Exception {
        this.postTimelineService = postTimelineService;
        this.output_topic_notification = output_topic_notification;

        reactiveKafkaMessageQueue.createFlow(input_topic_post, this::createPostTimeline);
    }

    private Flux<Tuple2<String, Map>> createPostTimeline(Map source) {
        return just(source)
                .filter(map -> source.containsKey("user") && source.containsKey("post") && source.containsKey("contacts"))
                .flatMapIterable(map -> (List<String>) source.get("contacts"))
                .flatMap(
                        user ->
                                postTimelineService.create(from(user, source.get("post").toString(), source.get("user").toString()))
                                    .map(
                                            ignored ->
                                                    Tuples.of(
                                                            this.output_topic_notification,
                                                            KAFKA_MESSAGE_BINDER.bindPostNotif(user, source.get("post").toString(), source.get("user").toString())
                                                    )
                                    )
                )
                .onErrorResume(throwable -> empty());
    }
}
