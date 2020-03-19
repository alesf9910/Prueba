package com.fyself.post.dist.rest.upload;

import com.fyself.post.facade.UploadFileFacade;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for Upload File.
 *
 * @author jmmarin
 * @since 0.1.0
 */
@RestController
@RequestMapping("/upload")
@Api(tags = "Upload Files", description = "Endpoint for upload files.")
public class UploadFileController extends Controller<UploadFileFacade> {

    @ApiSecuredOperation
    @ApiOperation(nickname = "upload_image", value = "Upload image", response = String.class)
    @RequestMapping(value = "/{type}", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity> uploadTax(@RequestBody Flux<Part> parts, @PathVariable String type, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.uploadImage(parts, type, context), exchange);
    }
}
