package com.fyself.post.facade.impl;

import com.fyself.post.facade.UploadFileFacade;
import com.fyself.post.service.post.FileService;
import com.fyself.post.service.post.contract.to.FileTO;
import com.fyself.post.service.post.contract.to.SignedFileTO;
import com.fyself.post.service.post.contract.to.UrlTo;
import com.fyself.post.service.system.UploadFileService;
import com.fyself.post.service.system.contract.to.ResourceCriteriaTO;
import com.fyself.post.service.system.contract.to.ResourceTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import com.fyself.seedwork.service.context.FySelfContext;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.fyself.post.service.system.FileUnSupportedException.fileUnSupportedException;
import static com.fyself.post.tools.ImageInformation.readImageInformation;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.core.io.buffer.DataBufferUtils.join;
import static reactor.core.publisher.Mono.error;
import static reactor.core.publisher.Mono.just;

@Facade("uploadFileFacade")
public class UploadFileFacadeImpl implements UploadFileFacade {

    private static final Logger logger = getLogger(UploadFileFacade.class);
    private UploadFileService uploadFileService;
    private String[] typesSupported;
    private FileService fileService;

    public UploadFileFacadeImpl(UploadFileService uploadFileService,
                                @Value("${application.file}") String[] typesSupported,
                                FileService fileService
    ) {
        this.uploadFileService = uploadFileService;
        this.typesSupported = typesSupported;
        this.fileService = fileService;
    }


    @Override
    public Mono<Result<String>> uploadImage(Mono<FilePart> part, String typeElement, FySelfContext context, boolean isPrivate) {
        logger.debug("Attempting to system image");
        return part.ofType(FilePart.class)
                .flatMap(filePart -> this.add(filePart, typeElement, isPrivate))
                .map(Result::successful);
    }

    @Override
    public Mono<Result<byte[]>> getFile(FileTO pdf, FySelfContext context)
    {
        return fileService.getFile(pdf,context).map(Result::successful);
    }

    @Override
    public Mono<Result<InputStreamResource>> getFilePrivate(ResourceCriteriaTO pdf, FySelfContext context)
    {
        return uploadFileService.getPrivate(pdf).map(Result::successful);
    }

    @Override
    public Mono<Result<String>> getUrl(UrlTo url, FySelfContext context)
    {
        return fileService.getUrl(url.getUrl(),context).map(Result::successful);
    }

    @Override
    public Mono<Result<Boolean>> deleteUrl(ResourceCriteriaTO url, FySelfContext context, boolean isPrivate)
    {
        return uploadFileService.deletePrivate(url)
                .map(Result::successful);
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private Mono<String> add(FilePart part, String typeElement, boolean isPrivate) {
        var ext = getExtensionByStringHandling(part.filename()).orElse("");
        var name = UUID.randomUUID().toString() + "." + ext;
        return just(name).flatMap(id -> this.save(id, typeElement, part, isPrivate));
    }


    private Mono<String> save(String name, String typeElement, FilePart part, boolean isPrivate) {
        return join(part.content())
                .map(DataBuffer::asByteBuffer)
                .flatMap(content -> {
                    if(isImage((part.headers().getContentType()))){
                        var a = readImageInformation(content.array());

                        var ratio = a.height / a.width;

                        var criteria = ResourceCriteriaTO.from(typeElement).withName(String.format("%.3f", ratio) + "_" + name);

                        if ( isPrivate )
                        {
                            if (!a.png)
                                return uploadFileService.addPrivate(ResourceTO.of(criteria, ByteBuffer.wrap(a.imageFile.getByteArray()), getMetadata(part.headers())));

                            return uploadFileService.addPrivate(ResourceTO.of(criteria, content, getMetadata(part.headers())));
                        }
                        else
                        {
                            if (!a.png)
                                return uploadFileService.add(ResourceTO.of(criteria, ByteBuffer.wrap(a.imageFile.getByteArray()), getMetadata(part.headers())));

                            return uploadFileService.add(ResourceTO.of(criteria, content, getMetadata(part.headers())));
                        }

                    } else {
                        var criteria = ResourceCriteriaTO.from(typeElement).withName(String.format(name));
                        if (isPrivate)
                            return uploadFileService.addPrivate(ResourceTO.of(criteria, content, getMetadata(part.headers())));
                        return uploadFileService.add(ResourceTO.of(criteria, content, getMetadata(part.headers())));
                    }
                });
    }


    private Map<String, String> getMetadata(HttpHeaders httpHeaders) {
        return httpHeaders.toSingleValueMap();
    }

    private Boolean isImage(MediaType type) {
        for (String mediaType : typesSupported) {
            if (type.toString().equals(mediaType)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Mono<Result<String>> uploadToS3(SignedFileTO to, FySelfContext context, boolean isPrivate) {
        var name = UUID.randomUUID().toString() + ".pdf";
        return just(name).flatMap(id -> this.saveSignedFile(id, to.getTypeFile(), to.getSignedFileS3(), isPrivate))
                .map(Result::successful);
    }

    private Mono<String> saveSignedFile(String name, String typeElement, byte[] part, boolean isPrivate) {
        return just(true).flatMap(unused -> {
            ByteBuffer byteBuffer = ByteBuffer.wrap(part);
            var criteria = ResourceCriteriaTO.from(typeElement).withName(String.format(name));
            if (isPrivate)
                return uploadFileService.addPrivate(ResourceTO.of(criteria, byteBuffer, Map.of()));
            return uploadFileService.add(ResourceTO.of(criteria, byteBuffer, Map.of()));
        });
    }
}
