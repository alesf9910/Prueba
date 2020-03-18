package com.fyself.post.service.stream.impl;

import com.fyself.post.service.stream.StreamService;
import com.fyself.seedwork.kafka.reactive.ReactiveKafkaMessageQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.Map;

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
    private final String topic_logstash_answer;

    public StreamServiceImpl(ReactiveKafkaMessageQueue queueManager,
                             @Value("${mspost.application.kafka.topics.output.elastic-answer}") String topic_logstash_answer) {
        this.queueManager = queueManager;
        this.topic_logstash_answer = topic_logstash_answer;
    }

    @Override
    public Mono<Void> putInPipelineAnswerElastic(Map message) {
        return this.send(message, topic_logstash_answer);
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
}
