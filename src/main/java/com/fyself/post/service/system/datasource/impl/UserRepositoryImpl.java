package com.fyself.post.service.system.datasource.impl;

import com.fyself.post.service.system.contract.to.ContactTO;
import com.fyself.post.service.system.contract.to.UserTO;
import com.fyself.post.service.system.datasource.UserRepository;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.restclient.FySelfHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

import java.util.Map;

import static reactor.core.publisher.Mono.just;

/**
 * Default implementation of {@link UserRepository}.
 *
 * @author jmmmarin
 * @author Alejandro
 * @since 0.0.3
 */
@Repository("userRepository")
public class UserRepositoryImpl implements UserRepository {

    private final FySelfHttpClient client;
    private final String baseUrl;
    private static final String USER_PATH = "/msauth/api/user/";
    private static final String CONTACT_PATH = "/ms-contacts/contacts/enrich-contact/";


    public UserRepositoryImpl(
            @Value("${application.security.ms.baseurl}") String baseUrl,
            FySelfHttpClient client
    ) {
        this.baseUrl = baseUrl;
        this.client = client;
    }


    @Override
    public Mono<Boolean> existUser(String userId, FySelfContext context) {
        return this.client
                .doGet(buildRequestUrl(USER_PATH, userId), Map.of(), buildRequestHeader(context), new ParameterizedTypeReference<Map>() {
                })
                .map(map -> map.get("_id").equals(userId))
                .switchIfEmpty(just(false))
                .onErrorResume(throwable -> just(false));
    }

    @Override
    public Mono<UserTO> getUser(String userId, FySelfContext context) {
        return this.client
                .doGet(buildRequestUrl(USER_PATH, userId), Map.of(), buildRequestHeader(context), new ParameterizedTypeReference<UserTO>() {
                })
                .map(map -> map)
                .switchIfEmpty(just(new UserTO()))
                .onErrorResume(throwable -> just(new UserTO()));
    }

    @Override
    public Mono<ContactTO> getContact(String userId, FySelfContext context) {
        return this.client
                .doGet(buildRequestUrl(CONTACT_PATH, userId), Map.of(), buildRequestHeader(context), new ParameterizedTypeReference<ContactTO>() {
                })
                .map(map -> map)
                .switchIfEmpty(just(new ContactTO()))
                .onErrorResume(throwable -> just(new ContactTO()));
    }

    /**
     * Build the url to make the request to ms-auth
     *
     * @param path   {@link String} Path of ms-auth
     * @param userId {@link String} User id to be check
     * @return {@link String} Url for ms-auth contact
     */
    private String buildRequestUrl(String path, String userId) {
        return String.format("%s%s%s", baseUrl, path, userId);
    }

    /**
     * Build header for request the ms-auth
     *
     * @param context {@link FySelfContext} FySelf context with the security token
     * @return {@link MultiValueMap} Map with the header values
     */
    private MultiValueMap<String, String> buildRequestHeader(FySelfContext context) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Authorization", getTokenFromContext(context));
        return headers;
    }


    /**
     * Extract from {@link FySelfContext} the security token
     *
     * @param context {@link FySelfContext}
     * @return {@link String} The security token from the user
     */
    private String getTokenFromContext(FySelfContext context) {
        return context.getHeaders().get("authorization").get(0);
    }
}
