package com.fyself.post.facade.impl;

import com.fyself.post.facade.BasicFacade;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.facade.stereotype.Facade;
import reactor.core.publisher.Mono;

import java.util.Map;

@Facade("basicFacade")
public class BasicFacadeImpl implements BasicFacade {
    @Override
    public Mono<Result<Map>> ok() {
        return Mono.just(Map.of("status", "ok")).map(Result::successful);
    }
}
