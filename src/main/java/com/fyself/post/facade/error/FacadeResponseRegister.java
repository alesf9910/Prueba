package com.fyself.post.facade.error;

import com.fyself.post.service.system.FileUnSupportedException;
import com.fyself.seedwork.error.ResponseRegister;
import com.fyself.seedwork.facade.Result;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.fyself.seedwork.error.Error.err;
import static com.fyself.seedwork.error.ErrorCode.INVALID_INPUT;
import static com.fyself.seedwork.facade.Result.failed;
import static java.util.List.of;
import static reactor.core.publisher.Mono.just;

/**
 * {@link ResponseRegister}'s implementation for the <code>Orchestration</code> layer.
 *
 * @author jmmarin
 * @since 0.1.0
 */
@Component
public class FacadeResponseRegister implements ResponseRegister<Mono<Result>> {
    private Map<Class<? extends Throwable>, Function<Throwable, Mono<Result>>> responses;

    @Override
    public Map<Class<? extends Throwable>, Function<Throwable, Mono<Result>>> getResponses() {
        if (null == responses) {
            this.initialize();
        }
        return responses;
    }

    //<editor-fold desc="Support methods">
    private void initialize() {
        responses = new HashMap<>();
        responses.put(FileUnSupportedException.class, tw -> this.getResponse((FileUnSupportedException) tw));
    }

    private Mono<Result> getResponse(FileUnSupportedException ex) {
        return just(failed(of(err(INVALID_INPUT, ex.getMessage()))));
    }
    //</editor-fold>
}
