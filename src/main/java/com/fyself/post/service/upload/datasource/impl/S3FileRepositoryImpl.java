package com.fyself.post.service.upload.datasource.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fyself.post.service.upload.datasource.S3FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.io.InputStream;
import java.util.UUID;

import static com.amazonaws.services.s3.AmazonS3ClientBuilder.standard;
import static reactor.core.publisher.Mono.just;

/**
 * Default implementation of {@link S3FileRepository}.
 *
 * @author jmmmarin
 * @since 0.0.1
 */
@Repository("s3FileRepository")
public class S3FileRepositoryImpl implements S3FileRepository {
    private static final String SUFFIX = "/";

    private final String bucketName;

    public S3FileRepositoryImpl(@Value("${mspost.application.aws.bucketName}") String bucketName) {
        this.bucketName = bucketName;
    }

    @Override
    public Mono<String> uploadFile(InputStream inputStream, String folderName, ObjectMetadata metadata) {
        String tempName = UUID.randomUUID().toString();
        String fileName = folderName + SUFFIX + tempName + "." + metadata.getContentType().substring(6);

        AmazonS3 s3client = standard().build();
        s3client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, metadata));

        return just(fileName);
    }
}
