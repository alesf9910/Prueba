package com.fyself.post.service.post.datasource;

import com.fyself.post.service.post.contract.to.FileTO;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

public interface FileRepository {
    Mono<byte[]> getFile(FileTO file, FySelfContext context);

    Mono<String> getUrl(String url, FySelfContext context);
}
