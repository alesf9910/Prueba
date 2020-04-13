package com.fyself.post.dist.rest.upload;

import com.fyself.post.facade.UploadFileFacade;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.*;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

/**
 * REST controller for Upload File.
 *
 * @author jmmarin
 * @author Yero
 * @since 0.2.0
 */
@RestController
@RequestMapping("/file")
@Api(tags = "Upload Files", description = "Endpoint for system files.")
public class UploadFileController extends Controller<UploadFileFacade> {

    @ApiSecuredOperation
    @ApiOperation(nickname = "upload_image", value = "Upload image", response = String.class)
    @RequestMapping(path = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity> uploadImage(@RequestPart Mono<FilePart> part, @RequestPart String type, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.uploadImage(part, type, context), exchange);
    }

}
