package com.fyself.post.service.system.contract;

import com.fyself.post.service.system.contract.to.ResourceCriteriaTO;
import com.fyself.post.service.system.contract.to.ResourceTO;
import com.fyself.seedwork.service.repository.file.domain.FileContent;
import com.fyself.seedwork.service.repository.file.domain.FileFields;
import com.fyself.seedwork.service.repository.file.query.FileCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Map;

import static org.mapstruct.factory.Mappers.getMapper;

/**
 * Binder for the {@link FileContent} system entity.
 *
 * @author Yero
 * @since 0.1.0
 */
@Mapper(imports = {Map.class, FileFields.class})
public interface ResourceBinder {
    ResourceBinder RESOURCE_BINDER = getMapper(ResourceBinder.class);

    @Mapping(target = "path", expression = "java(String.format(\"%s/%s/%s\", source.getCriteria().getFolder(), source.getCriteria().getName()))")
    @Mapping(target = "credentials", expression = "java(Map.of(FileFields.AWS_BUCKET, bucket))")
    FileContent bind(ResourceTO source, String bucket);

    @Mapping(target = "path", expression = "java(String.format(\"%s/%s/%s\", source.getFolder(), source.getName()))")
    @Mapping(target = "credentials", expression = "java(Map.of(FileFields.AWS_BUCKET, bucket))")
    FileCriteria bind(ResourceCriteriaTO source, String bucket);
}
