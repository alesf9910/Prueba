package com.fyself.post.service.upload;

import reactor.core.publisher.Mono;

import java.io.InputStream;

/**
 * Service interface for upload Image.
 *
 * @author jmmarin
 * @since 0.1.0
 */
public interface UploadFileService {

    Mono<String> uploadImage(InputStream inputStream, String typeElement);
}
