package com.fyself.post.facade;

import com.fyself.post.service.post.contract.to.FileTO;
import com.fyself.post.service.post.contract.to.SignedFileTO;
import com.fyself.post.service.post.contract.to.UrlTo;
import com.fyself.post.service.system.contract.to.ResourceCriteriaTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface UploadFileFacade {

    Mono<Result<String>> uploadImage(Mono<FilePart> part, String typeElement, FySelfContext context, boolean isPrivate);

    Mono<Result<String>> uploadToS3(SignedFileTO to, FySelfContext context, boolean isPrivate);

    Mono<Result<byte[]>> getFile(FileTO pdf, FySelfContext context);

    Mono<Result<InputStreamResource>> getFilePrivate(ResourceCriteriaTO pdf, FySelfContext context);

    Mono<Result<String>> getUrl(UrlTo url, FySelfContext context);

    Mono<Result<Boolean>> deleteUrl(ResourceCriteriaTO url, FySelfContext context, boolean isPrivate);
}
