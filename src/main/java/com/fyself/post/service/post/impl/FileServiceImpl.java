package com.fyself.post.service.post.impl;

import com.fyself.post.service.post.FileService;
import com.fyself.post.service.post.contract.to.FileTO;
import com.fyself.post.service.post.datasource.FileRepository;
import com.fyself.seedwork.service.context.FySelfContext;
import com.fyself.seedwork.service.repository.restclient.FySelfHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service("fileService")
@Validated
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public Mono<byte[]> getFile(@NotNull @Valid FileTO file, FySelfContext content)
    {
        return fileRepository.getFile(file,content);
    }

    @Override
    public Mono<String> getUrl(@NotNull @Valid String url, FySelfContext content)
    {
        return fileRepository.getUrl(url,content);
    }
}
