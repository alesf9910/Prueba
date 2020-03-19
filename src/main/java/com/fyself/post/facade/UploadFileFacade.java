package com.fyself.post.facade;

import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.context.FySelfContext;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UploadFileFacade {

    Mono<Result<String>> uploadImage(Flux<Part> parts, String typeElement, FySelfContext context);

}
