package com.fyself.post.configuration;

import com.fyself.seedwork.i18n.MessageContextHolder;
import com.fyself.seedwork.kafka.configuration.QueuesConfiguration;
import com.fyself.seedwork.service.repository.mongodb.listeners.CascadeReferenceMongoEventListener;
import com.fyself.seedwork.web.configuration.DefaultDispatcherContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;
import javax.validation.Validator;
import static com.fyself.post.Main.TITLE;
import static com.fyself.post.Main.VERSION;
import static java.util.Collections.emptyList;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.any;
import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@Configuration
public class DefaultContext {
    /**
     * Configuration class for the dispatcher (web) distribution context.
     */
    @Configuration
    @Import(DefaultDispatcherContext.class)
    @ComponentScan("com.fyself.post.dist.rest")
    public class DispatcherContext {

    }

    @Bean
    public CascadeReferenceMongoEventListener CascadingMongoEventListener() {
        return new CascadeReferenceMongoEventListener();
    }


    /**
     * Configuration class for the data source context.
     *
     * @author evdelacruz
     * @since 0.1.0
     */
    @Configuration
    @ComponentScan(basePackages={"com.fyself.post.facade.impl",
            "com.fyself.post.facade.error",
            "com.fyself.post.service.*.impl",
            "com.fyself.post.service.*.contract.validation",
            "com.fyself.post.service.*.contract.validation.*",
            "com.fyself.post.service.*.datasource",
                                 "com.fyself.seedwork.facade"})
    @EnableReactiveMongoRepositories("com.fyself.post.service.*.datasource")
    public class DataSourceContext {
        @Bean
        public MessageSource messageSource() {
            return MessageContextHolder.getSource();
        }
    }

    /**
     * Configuration class for the validation context.
     */
    @Configuration
    public class ValidationContext {

        @Bean
        public Validator validator(MessageSource messageSource) {
            LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
            factory.setValidationMessageSource(messageSource);
            return factory;
        }
    }

    /**
     * Configuration class for the streaming context.
     */
    @Configuration
    @Import(QueuesConfiguration.class)
    @ComponentScan("com.fyself.post.dist.stream")
    public class StreamContext {

    }

    /**
     * Configuration class for the swagger (rest api tests) context.
     */
    @Configuration
    @EnableSwagger2WebFlux
    public class SwaggerContext {
        private final Contact DEFAULT_CONTACT = new Contact("", "", "");
        private final ApiInfo INFO = new ApiInfo(
                null != TITLE ? TITLE : "SEARCH MICROSERVICE",
                "Component for Profile Management",
                null != VERSION ? VERSION : "0.0.0",
                "urn:tos",
                DEFAULT_CONTACT,
                null, null, emptyList());
        private @Value("${application.name}") String path;

        @Bean
        public Docket api() {
            return new Docket(SWAGGER_2)
                    .apiInfo(INFO)
                    .select().apis(any())
                    .paths(PathSelectors.any())
                    .build();
        }

        @Bean
        public UiConfiguration uiConfig(){
            return UiConfigurationBuilder.builder()
                    .deepLinking(true)
                    .displayOperationId(false)
                    .defaultModelsExpandDepth(2)
                    .defaultModelExpandDepth(1)
                    .defaultModelRendering(ModelRendering.MODEL)
                    .displayRequestDuration(true)
                    .docExpansion(DocExpansion.LIST)
                    .filter(false)
                    .maxDisplayedTags(null)
                    .operationsSorter(OperationsSorter.METHOD)
                    .showExtensions(false)
                    .tagsSorter(TagsSorter.ALPHA)
                    .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
                    .validatorUrl(null)
                    .build();
        }
    }

}
