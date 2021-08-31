package com.fyself.post.service.system.datasource.impl;

import com.fyself.post.service.system.contract.to.EnterpriseTO;
import com.fyself.post.service.system.datasource.EnterpriseRepository;
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
 * Default implementation for {@link EnterpriseRepository}
 *
 * @author Miguel
 * @since 0.0.1
 */
@Repository("enterpriseRepository")
public class EnterpriseRepositoryImpl implements EnterpriseRepository {
    private final FySelfHttpClient client;
    private final String baseUrl;
    private static final String ENTERPRISE_PATH = "/ms-business/enterprise/is-deleted/";

    public EnterpriseRepositoryImpl(
            @Value("${application.security.ms.baseurl}") String baseUrl,
            FySelfHttpClient client
    ) {
        this.baseUrl = baseUrl;
        this.client = client;
    }

    @Override
    public Mono<Boolean> getEnterprise(String enterpriseId, FySelfContext context) {
        return this.client
                .doGet(buildRequestUrl(ENTERPRISE_PATH, enterpriseId), Map.of(), buildRequestHeader(context), new ParameterizedTypeReference<Boolean>() {
                });
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
