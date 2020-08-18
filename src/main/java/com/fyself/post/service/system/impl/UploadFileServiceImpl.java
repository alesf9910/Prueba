package com.fyself.post.service.system.impl;

import com.fyself.post.service.system.UploadFileService;
import com.fyself.post.service.system.contract.to.ResourceCriteriaTO;
import com.fyself.post.service.system.contract.to.ResourceTO;
import com.fyself.seedwork.service.repository.file.FileRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.fyself.post.service.system.contract.ResourceBinder.RESOURCE_BINDER;
import static org.slf4j.LoggerFactory.getLogger;

@Service("uploadFileService")
public class UploadFileServiceImpl implements UploadFileService {

    private static final Logger logger = getLogger(UploadFileService.class);
    private final FileRepository fileRepository;
    private final String bucket;
    private final String url;

    public UploadFileServiceImpl(FileRepository fileRepository,
                                 @Value("${application.aws.bucket}") String bucket,
                                 @Value("${mspost.application.aws.url}") String url
    ) {
        this.fileRepository = fileRepository;
        this.bucket = bucket;
        this.url = url;
    }

    @Override
    public Mono<String> add(ResourceTO resource) {
        return this.fileRepository.save(RESOURCE_BINDER.bind(resource, bucket))
                .filter(Boolean::booleanValue)
                .map(ing -> String.format("%s/%s/%s", url, resource.getCriteria().getFolder(), resource.getCriteria().getName()));
    }

    @Override
    public Mono<Boolean> delete(ResourceCriteriaTO criteria) {
        return this.fileRepository.delete(RESOURCE_BINDER.bind(criteria, bucket));
    }

    @Override
    public Mono<InputStreamResource> get(ResourceCriteriaTO criteria) {
        return fileRepository.getContent(RESOURCE_BINDER.bind(criteria, bucket)).map(InputStreamResource::new);
    }
}
