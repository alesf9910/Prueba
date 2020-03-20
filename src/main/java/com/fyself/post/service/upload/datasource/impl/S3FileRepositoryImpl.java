package com.fyself.post.service.upload.datasource.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fyself.post.service.upload.datasource.S3FileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.synchronoss.cloud.nio.multipart.util.IOUtils;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import static com.amazonaws.services.s3.AmazonS3ClientBuilder.standard;
import static reactor.core.publisher.Mono.empty;
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
    public Mono<String> uploadFile(InputStream inputStream, String folderName) {
        try {
            String tempName = UUID.randomUUID().toString();
            File targetFile = new File("src/main/resources/" + tempName + ".tmp");

            java.nio.file.Files.copy(inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            IOUtils.closeQuietly(inputStream);

            AmazonS3 s3client = standard().build();

            String fileName = folderName + SUFFIX + tempName + ".jpg";

            s3client.putObject(new PutObjectRequest(bucketName, fileName, targetFile));

            targetFile.delete();

            return just(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return empty();
        }


    }
}
