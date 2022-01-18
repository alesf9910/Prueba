package com.fyself.post.facade;

import com.fyself.post.service.firm.contract.to.FirmPdfTO;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

/**
 * Facade implementation for firm files.
 *
 * @author JKlo
 * @since 0.0.1
 */
public interface FirmFascade
{
    Mono<Result<Object>> firmPdf(FirmPdfTO withPost, FySelfContext context);
}
