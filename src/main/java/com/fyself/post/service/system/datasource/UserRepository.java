package com.fyself.post.service.system.datasource;

import com.fyself.post.service.system.contract.to.UserTO;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

/**
 *
 *
 * @author jmmarin
 * @since 0.0.2
 */
public interface UserRepository  {

    Mono<Boolean> existUser(String userId, FySelfContext context);
    Mono<UserTO> getUser(String userId, FySelfContext context);
}
