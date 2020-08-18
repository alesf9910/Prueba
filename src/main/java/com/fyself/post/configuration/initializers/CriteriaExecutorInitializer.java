package com.fyself.post.configuration.initializers;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import static com.fyself.seedwork.service.repository.mongodb.criteria.CriteriaExecutor.setTemplate;


public class CriteriaExecutorInitializer implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        ReactiveMongoTemplate template = event.getApplicationContext().getBean(ReactiveMongoTemplate.class);
        setTemplate(template);
    }
}
