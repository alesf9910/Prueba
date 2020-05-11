package com.fyself.post.dist.stream;

import com.fyself.post.service.post.PostService;
import com.fyself.post.service.post.PostTimelineService;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.seedwork.kafka.reactive.ReactiveKafkaMessageQueue;
import com.fyself.seedwork.kafka.stereotype.Stream;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Map;

import static com.fyself.post.service.post.contract.to.PostTimelineTO.from;
import static com.fyself.post.service.stream.contract.KafkaMessageBinder.KAFKA_MESSAGE_BINDER;
import static com.fyself.seedwork.util.JsonUtil.MAPPER;
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

    private final String input_topic_new;
    private final PostTimelineService postTimelineService;
    private final String output_topic_notification;
    private final PostService postService;


    public GenericKStreamProcessor(
            @Value("${mspost.application.kafka.topics.input.post}") String input_topic_post,
            @Value("${mspost.application.kafka.topics.input.new}") String input_topic_new,
            @Value("${mspost.application.kafka.topics.output.notification-socket}") String output_topic_notification,
            ReactiveKafkaMessageQueue reactiveKafkaMessageQueue, PostTimelineService postTimelineService, PostService postService) throws Exception {
        this.input_topic_new = input_topic_new;
        this.postTimelineService = postTimelineService;
        this.output_topic_notification = output_topic_notification;
        this.postService = postService;

        reactiveKafkaMessageQueue.createFlow(input_topic_post, this::createPostTimeline);
        reactiveKafkaMessageQueue.createSink(input_topic_new, this::createPost);
    }

    private Mono<Void> createPost(Map map) {
        try {
            PostTO post = MAPPER.readValue(MAPPER.writeValueAsString(map), PostTO.class);
            post.setOwner(map.get("owner").toString());
            return postService.create(post);
        }catch (Exception e){e.printStackTrace();}
        return empty();
    }

    private Flux<Tuple2<String, Map>> createPostTimeline(Map source) {
        return just(source)
                .filter(map ->
                        source.containsKey("user") &&
                                source.containsKey("post") &&
                                source.containsKey("contact")
                )
                .map(contact -> source.get("contact").toString())
                .flatMap(contact -> postTimelineService.create( from(contact, source.get("post").toString(), source.get("user").toString())))
                .map(user -> Tuples.of( this.output_topic_notification, KAFKA_MESSAGE_BINDER.bindPostNotif(user, source.get("post").toString(), source.get("user").toString()))
                )
                .flux()
                .onErrorResume(throwable -> empty());
    }
}


