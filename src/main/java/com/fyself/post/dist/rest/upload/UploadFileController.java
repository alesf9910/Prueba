package com.fyself.post.dist.rest.upload;

import com.fyself.post.dist.rest.comments.CommentController;
import com.fyself.post.facade.UploadFileFacade;
import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.contract.to.FileTO;
import com.fyself.post.service.post.contract.to.criteria.CommentCriteriaTO;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javaxt.http.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @PostMapping(value="/get-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiSecuredOperation
    @ApiOperation(nickname = "get_file", value = "Search comments", response = String.class)
    public Mono<ResponseEntity> search(@RequestBody FileTO file, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.getFile(file, context), exchange);
    }

}
