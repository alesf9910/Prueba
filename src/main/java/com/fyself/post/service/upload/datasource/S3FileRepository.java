package com.fyself.post.service.upload.datasource;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import reactor.core.publisher.Mono;

import java.io.InputStream;

/**
 * Repository interface for upload file to S3.
 *
 * @author jmmarin
 * @since 0.1.0
 */
public interface S3FileRepository {

    Mono<String> uploadFile(InputStream inputStream, String folderName, ObjectMetadata metadata);

    Mono<S3Object> downloadFile(String folderName, String fileName);
}
