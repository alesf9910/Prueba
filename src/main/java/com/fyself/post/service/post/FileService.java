package com.fyself.post.service.post;

import com.fyself.post.service.post.contract.to.FileTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface FileService {

    Mono<byte[]> getFile(@NotNull @Valid FileTO file, FySelfContext context);

    Mono<String> getUrl(@NotNull @Valid String url, FySelfContext context);
}
