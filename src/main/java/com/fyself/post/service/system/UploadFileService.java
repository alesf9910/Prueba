package com.fyself.post.service.system;

import com.fyself.post.service.system.contract.to.ResourceCriteriaTO;
import com.fyself.post.service.system.contract.to.ResourceTO;
import org.springframework.core.io.InputStreamResource;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * Service interface for system Image.
 *
 * @author jmmarin
 * @since 0.1.0
 */
public interface UploadFileService {

    Mono<String> add(ResourceTO resource);

    Mono<Boolean> delete(ResourceCriteriaTO criteria);

    Mono<InputStreamResource> get(@NotNull @Valid ResourceCriteriaTO criteria);

    Mono<String> addPrivate(ResourceTO resource);

    Mono<Boolean> deletePrivate(ResourceCriteriaTO criteria);
}
