package com.fyself.post.facade;

import com.fyself.post.service.post.contract.to.FileTO;
import com.fyself.post.service.post.contract.to.UrlTo;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

public interface UploadFileFacade {

    Mono<Result<String>> uploadImage(Mono<FilePart> part, String typeElement, FySelfContext context);

    Mono<Result<byte[]>> getFile(FileTO pdf, FySelfContext context);

    Mono<Result<String>> getUrl(UrlTo url, FySelfContext context);

}
