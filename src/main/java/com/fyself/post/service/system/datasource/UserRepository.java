package com.fyself.post.service.system.datasource;

import com.fyself.post.service.system.contract.to.ContactTO;
import com.fyself.post.service.system.contract.to.UserTO;
import com.fyself.seedwork.service.context.FySelfContext;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

/**
 * @author jmmarin
 * @author Alejandro
 * @since 0.0.3
 */
public interface UserRepository {

    Mono<Boolean> existUser(String userId, FySelfContext context);

    Mono<UserTO> getUser(String userId, FySelfContext context);

    Mono<Set<String>> loadUsersWorkspace(String enterprise, FySelfContext context);

    Mono<ContactTO> getContact(String userId, FySelfContext context);
}
