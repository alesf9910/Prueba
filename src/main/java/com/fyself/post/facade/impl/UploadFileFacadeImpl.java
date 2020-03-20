package com.fyself.post.facade.impl;

import com.fyself.post.facade.UploadFileFacade;
import com.fyself.post.service.upload.UploadFileService;
import com.fyself.post.tools.InputStreamCollector;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.context.FySelfContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

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
    public Mono<Result<String>> uploadImage(Flux<FilePart> parts, String typeElement, FySelfContext context) {
        logger.debug("Attempting to upload image");
        return parts
                .ofType(FilePart.class)
                .filter(filePart -> supported(filePart.headers().getContentType()))
//                        .switchIfEmpty(error())  TODO crear error de tipo no soportado
                .flatMap(Part::content)
                .collect(InputStreamCollector::new, (t, dataBuffer) -> t.collectInputStream(dataBuffer.asInputStream()))
                .flatMap(inputStreamCollector -> uploadFileService.uploadImage(inputStreamCollector.getInputStream(), typeElement))
                .map(Result::successful);
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
