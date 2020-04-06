package com.fyself.post.service.system.datasource.impl;

import com.fyself.post.service.system.contract.to.UserTO;
import com.fyself.post.service.system.datasource.UserRepository;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

import static reactor.core.publisher.Mono.just;

/**
 * Default implementation of {@link UserRepository}.
 *
 * @author jmmmarin
 * @since 0.0.2
 */
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    private final String baseUrl;
    private final WebClient client;
    private static final String USER_PATH = "/api/user/";

    public UserRepositoryImpl(
            @Value("${mspost.application.security.ms-auth.baseUrl}") String baseUrl,
            WebClient.Builder webClientBuilder
    ) {
        this.baseUrl = baseUrl;
        this.client = webClientBuilder.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).baseUrl(baseUrl).build();
    }


    @Override
    public Mono<Boolean> existUser(String userId, FySelfContext context) {
        String url = String.format("%s%s%s", baseUrl, USER_PATH, userId);
        String token = context.getHeaders().get("authorization").get(0);
        return this.client.get().uri(url)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Map.class)
                .map(map -> map.get("_id").equals(userId))
                .switchIfEmpty(just(false))
                .onErrorResume(throwable -> just(false));
    }

    @Override
    public Mono<UserTO> getUser(String userId, FySelfContext context) {
        String url = String.format("%s%s%s", baseUrl, USER_PATH, userId);
        String token = context.getHeaders().get("authorization").get(0);
        return this.client.get().uri(url)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(UserTO.class)
                .map(map -> map)
                .switchIfEmpty(just(new UserTO()))
                .onErrorResume(throwable -> just(new UserTO()));
    }
}
