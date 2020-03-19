package com.fyself.post.service.upload.datasource;

import reactor.core.publisher.Mono;

import java.io.InputStream;

/**
 * Repository interface for upload file to S3.
 *
 * @author jmmarin
 * @since 0.1.0
 */
public interface S3FileRepository {

    Mono<String> uploadFile(InputStream inputStream, String folderName);
}
