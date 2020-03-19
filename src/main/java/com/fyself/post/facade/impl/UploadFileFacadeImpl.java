package com.fyself.post.facade.impl;

import com.fyself.post.facade.UploadFileFacade;
import com.fyself.post.service.upload.UploadFileService;
import com.fyself.post.tools.InputStreamCollector;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.context.FySelfContext;
import org.slf4j.Logger;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

@Facade("uploadFileFacade")
public class UploadFileFacadeImpl implements UploadFileFacade {

    private static final Logger logger = getLogger(UploadFileFacade.class);
    private UploadFileService uploadFileService;

    public UploadFileFacadeImpl(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }


    @Override
    public Mono<Result<String>> uploadImage(Flux<Part> parts, String typeElement, FySelfContext context) {
        logger.debug("Attempting to upload image");
        return parts
                .filter(part -> part instanceof FilePart)
                .ofType(FilePart.class)
                .flatMap(Part::content)
                .collect(InputStreamCollector::new, (t, dataBuffer) -> t.collectInputStream(dataBuffer.asInputStream()))
                .flatMap(inputStreamCollector -> uploadFileService.uploadImage(inputStreamCollector.getInputStream(), typeElement))
                .map(Result::successful);
    }
}
