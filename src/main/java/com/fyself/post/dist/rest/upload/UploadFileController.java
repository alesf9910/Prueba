package com.fyself.post.dist.rest.upload;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.fyself.post.facade.UploadFileFacade;
import com.fyself.seedwork.facade.Result;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.io.File;
import java.io.IOException;

/**
 * REST controller for Upload File.
 *
 * @author jmmarin
 * @since 0.1.0
 */
@RestController
@RequestMapping("/file")
@Api(tags = "Upload Files", description = "Endpoint for upload files.")
public class UploadFileController extends Controller<UploadFileFacade> {

    @Autowired
    UploadFileFacade uploadFileFacade;

    @ApiSecuredOperation
    @ApiOperation(nickname = "upload_image", value = "Upload image", response = String.class)
    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity> uploadImage(@RequestPart Mono<FilePart> part, @RequestPart String type, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.uploadImage(part, type, context), exchange);
    }

    @ApiSecuredOperation
    @ApiOperation(nickname = "upload_image", value = "Upload image", response = File.class)
    @RequestMapping(path = "/download/post/{url}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<ResponseEntity> downloadImage(@PathVariable String url, @ApiIgnore ServerWebExchange exchange) {
        return uploadFileFacade.downloadImage(url, null).map(this::getResponse);
    }

    private ResponseEntity getResponse(Result<S3Object> s3ObjectResult) {
        S3Object s3Object = s3ObjectResult.getVal();

        HttpHeaders headers = new HttpHeaders();
        byte[] media = new byte[0];
        try {
            media = IOUtils.toByteArray(s3Object.getObjectContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        headers.setContentType(MediaType.valueOf(s3Object.getObjectMetadata().getContentType()));
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());

        return new ResponseEntity<>(media, headers, HttpStatus.OK);
    }
}
