package com.fyself.post.service.post.datasource.impl;

import com.fyself.post.service.post.contract.to.FileTO;
import com.fyself.post.service.post.datasource.FileRepository;
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

@Repository("fileRepository")
public class FileRepositoryImpl implements FileRepository {

    private final FySelfHttpClient client;
    private final String baseUrl;
    private static final String USER_PATH = "/post/";

    public FileRepositoryImpl(
            FySelfHttpClient client,
            @Value("${mspost.application.security.ms.cloudfront-baseurl}") String baseUrl
    ) {
        this.client = client;
        this.baseUrl = baseUrl;
    }

    @Override
    public Mono<byte[]> getFile(FileTO file, FySelfContext context) {
        String url = String.format("%s%s%s", baseUrl, USER_PATH, file.getFile());
        String token = context.getHeaders().get("authorization").get(0);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type","application/pdf");
        headers.add("Authorization", token);
        return this.client
                .doGet(url, Map.of(),headers,new ParameterizedTypeReference<byte[]>() {})
                .map(map -> map)
                .onErrorResume(throwable -> Mono.empty());
        /*return Mono.empty()
                .doOnSuccess(o -> {
                    String url = String.format("%s%s%s", baseUrl, USER_PATH, file.getFile());
                    String token = context.getHeaders().get("authorization").get(0);
                    MultiValueMap<String, String> headers = new HttpHeaders();
                    headers.add("Content-Type","application/octet-stream");
                    headers.add("Authorization", token);
                    this.client
                            .doGet(url, Map.of(),headers,new ParameterizedTypeReference<byte[]>() {})
                            .map(map -> map)
                            .onErrorResume(throwable -> Mono.empty()).subscribe();
                }).thenReturn(this.pdf());*/
    }

    public byte[] pdf()
    {
        byte[] data = new byte[2];
        return data;
    }
}
