package com.fyself.post.service.upload;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import reactor.core.publisher.Mono;

import java.io.InputStream;

/**
 * Service interface for upload Image.
 *
 * @author jmmarin
 * @since 0.1.0
 */
public interface UploadFileService {

    Mono<String> uploadImage(InputStream inputStream, String typeElement, ObjectMetadata metadata);
    Mono<S3Object> downloadImage(String folderName, String fileName);
}
