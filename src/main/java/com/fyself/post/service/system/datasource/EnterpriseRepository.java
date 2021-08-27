package com.fyself.post.service.system.datasource;

import com.fyself.post.service.system.contract.to.EnterpriseTO;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

public interface EnterpriseRepository {
    Mono<Boolean> getEnterprise(String enterpriseId, FySelfContext context);
}
