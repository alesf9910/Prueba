package com.fyself.post.facade;

import com.fyself.seedwork.facade.Result;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BasicFacade {
    Mono<Result<Map>> ok();
}
