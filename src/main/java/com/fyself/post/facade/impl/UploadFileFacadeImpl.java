package com.fyself.post.facade.impl;

import com.fyself.post.facade.UploadFileFacade;
import com.fyself.post.service.system.UploadFileService;
import com.fyself.post.service.system.contract.to.ResourceTO;
import com.fyself.post.tools.InputStreamCollector;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.context.FySelfContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import static com.fyself.post.service.system.FileUnSupportedException.fileUnSupportedException;
import static org.slf4j.LoggerFactory.getLogger;
import static reactor.core.publisher.Mono.error;

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
        return part
                .ofType(FilePart.class)
                .filter(filePart -> supported(filePart.headers().getContentType()))
                // TODO: Improve this validations !!
                .switchIfEmpty(error(fileUnSupportedException("fyself.facade.post.upload.file.unsupported")))
//                .flatMap(filePart -> filePart.content()
//                        .collect(InputStreamCollector::new, (t, dataBuffer) -> t.collectInputStream(dataBuffer.asInputStream()))
//                        .flatMap(inputStreamCollector ->
//                                uploadFileService.add(
//                                        ResourceTO.of(cri,inputStreamCollector.getInputStream())
//                                    , typeElement, getMetadata(filePart.headers())
//                                )
//                        )
                .map(filePart -> "https://blog.fyself.com/wp-content/uploads/2020/03/quie-soy-en-internet-735x400.png")
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
