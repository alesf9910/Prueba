package com.fyself.post.service.stream.impl;

import com.fyself.post.service.stream.StreamService;
import com.fyself.seedwork.kafka.reactive.ReactiveKafkaMessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.error;

/**
 * Default implementation of {@link StreamService}.
 *
 * @author jmmarin
 * @since 0.1.0
 */
@Service("streamService")
public class StreamServiceImpl implements StreamService {
    private static final Logger logger = LoggerFactory.getLogger(StreamService.class);
    private final ReactiveKafkaMessageQueue queueManager;
    private final String topic_logstash_post;
    private final String topic_logstash_answer;
    private final String output_topic_notification;
    /**
     * Send message ms-post to ms-chat
     */
    private final String topic_post_chat;


    public StreamServiceImpl(ReactiveKafkaMessageQueue queueManager,
                             @Value("${mspost.application.kafka.topics.output.elastic-post}") String topic_logstash_post ,
                             @Value("${mspost.application.kafka.topics.output.elastic-answer}") String topic_logstash_answer,
                             @Value("${mspost.application.kafka.topics.output.post-chat}") String topic_post_chat,
                             @Value("${mspost.application.kafka.topics.output.notification-socket}") String output_topic_notification) {
        this.queueManager = queueManager;
        this.topic_logstash_post = topic_logstash_post;
        this.topic_logstash_answer = topic_logstash_answer;
        this.topic_post_chat = topic_post_chat;
        this.output_topic_notification = output_topic_notification;
    }

    @Override
    public Mono<Void> putInPipelineAnswerElastic(Map message) {
        return this.send(message, topic_logstash_answer);
    }

    @Override
    public Mono<Void> putInPipelinePostElastic(Map message) {
        return this.send(message, topic_logstash_post);
    }

    /**
     * This method send message to kafka with post id to delete notification
     *
     * @param  message  Map of message with type of message and post id
     */
    @Override
    public Mono<Void> putInPipelineDeletePostNotification(Map message) {
        return this.send(message, topic_post_chat);
    }

    @Override
    public Mono<Void> putInPipelinePostNotification(Map message)
    {
        return this.send(message, output_topic_notification);
    }

    //<editor-fold desc="Encapsulation">
    private Mono<Void> send(Map message, String topic) {
        try {
            logger.trace("Sending kafka messages");
            this.queueManager.send(Tuples.of(topic, message));
            return empty();
        } catch (Exception ex) {
            logger.warn("Impossible send kafka message", ex);
            return error(ex);
        }
    }
    //</editor-fold>

    @Override
    public Mono<Boolean> containAllConsumerGroup(Collection<String> groupIds, Set<String> setString)
    {
        return Mono.just(queueManager.containAllConsumerGroup(groupIds, setString));
    }
}
