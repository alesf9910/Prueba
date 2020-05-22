package com.fyself.post.facade.impl;

import com.fyself.post.facade.UploadFileFacade;
import com.fyself.post.service.system.UploadFileService;
import com.fyself.post.service.system.contract.to.ResourceCriteriaTO;
import com.fyself.post.service.system.contract.to.ResourceTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.context.FySelfContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

import static com.fyself.post.service.system.FileUnSupportedException.fileUnSupportedException;
import static com.fyself.post.tools.StringUtils.normalize;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.core.io.buffer.DataBufferUtils.join;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

@Facade("uploadFileFacade")
public class UploadFileFacadeImpl implements UploadFileFacade {

    private static final Logger logger = getLogger(UploadFileFacade.class);
    private UploadFileService uploadFileService;
    private String[] typesSupported;

    public UploadFileFacadeImpl(UploadFileService uploadFileService,
                                @Value("${application.file}") String[] typesSupported) {
        this.uploadFileService = uploadFileService;
        this.typesSupported = typesSupported;
    }


    @Override
    public Mono<Result<String>> uploadImage(Mono<FilePart> part, String typeElement, FySelfContext context) {
        logger.debug("Attempting to system image");
        return part.ofType(FilePart.class)
                .flatMap(filePart -> this.add(filePart, typeElement))
                .map(Result::successful);
    }

    private Mono<String> add(FilePart part, String typeElement) {
        var name = UUID.randomUUID().toString();
        return just(name).flatMap(id -> this.save(id, typeElement, part));
    }


    private Mono<String> save(String name, String typeElement, FilePart part) {
        return join(part.content())
                .filter(filePart -> supported(part.headers().getContentType()))
                .switchIfEmpty(error(fileUnSupportedException("fyself.facade.post.upload.file.unsupported")))
                .map(DataBuffer::asByteBuffer)
                .flatMap(content -> {
                    var criteria = ResourceCriteriaTO.from(typeElement).withName(name);
                    return uploadFileService.add(ResourceTO.of(criteria, content, getMetadata(part.headers())));
                });
    }


    private Map<String, String> getMetadata(HttpHeaders httpHeaders) {
        return httpHeaders.toSingleValueMap();
    }

    private Boolean supported(MediaType type) {
        for (String mediaType : typesSupported) {
            if (type.toString().equals(mediaType)) {
                return true;
            }
        }
        return false;
    }
}
