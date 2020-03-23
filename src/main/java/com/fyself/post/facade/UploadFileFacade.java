package com.fyself.post.facade;

import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UploadFileFacade {

    Mono<Result<String>> uploadImage(Mono<FilePart> part, String typeElement, FySelfContext context);

}
