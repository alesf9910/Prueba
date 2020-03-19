package com.fyself.post.service.upload.impl;

import com.fyself.post.service.upload.UploadFileService;
import com.fyself.post.service.upload.datasource.S3FileRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.InputStream;

import static org.slf4j.LoggerFactory.getLogger;

@Service("uploadFileService")
public class UploadFileServiceImpl implements UploadFileService {

    private static final Logger logger = getLogger(UploadFileService.class);
    private final S3FileRepository repository;

    public UploadFileServiceImpl(S3FileRepository repository) {

        this.repository = repository;
    }


    @Override
    public Mono<String> uploadImage(InputStream inputStream, String typeElement) {
        return repository.uploadFile(inputStream, typeElement);
    }
}
