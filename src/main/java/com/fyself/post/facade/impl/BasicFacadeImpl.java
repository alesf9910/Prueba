package com.fyself.post.facade.impl;

import com.fyself.post.facade.BasicFacade;
import com.fyself.post.service.stream.StreamService;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.DataSourceCommunicationException;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.util.*;

import static reactor.core.publisher.Mono.error;

@Facade("basicFacade")
public class BasicFacadeImpl implements BasicFacade {
    private StreamService streamService;
    private String stringTopics;
    private String groupId;
    private final String topicPrefix;
    private String enviromentPrefix;

    BasicFacadeImpl(StreamService streamService,
                    @Value("${mspost.application.kafka.topics-verify.string}") String stringTopics,
                    @Value("${application.kafka.groupId}") String groupId,
                    @Value("${application.kafka.topicPrefix}") String topicPrefix,
                    @Value("${application.kafka.enviromentPrefix}") String enviromentPrefix
                    )
    {
        this.streamService = streamService;
        this.stringTopics = stringTopics;
        this.groupId = groupId;
        this.topicPrefix = topicPrefix;
        this.enviromentPrefix = enviromentPrefix;
    }

    @Override
    public Mono<Result<Map>> ok() {
        Collection<String> g = Arrays.asList(groupId);
        Set<String> setString = new HashSet<>();
        for (String val : stringTopics.split(","))
            setString.add(topicPrefix+"-"+enviromentPrefix+"-"+val.replaceAll("\\s+",""));
        return streamService.containAllConsumerGroup(g, setString)
                .filter(aBoolean -> aBoolean)
                .map(aBoolean -> Map.of("status", "OK" ))
                .switchIfEmpty(error(DataSourceCommunicationException::new))
                .map(Result::successful);
    }
}
