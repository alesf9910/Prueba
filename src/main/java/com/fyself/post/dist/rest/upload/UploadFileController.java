package com.fyself.post.dist.rest.upload;

import com.fyself.post.dist.rest.comments.CommentController;
import com.fyself.post.facade.UploadFileFacade;
import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.contract.to.FileTO;
import com.fyself.post.service.post.contract.to.UrlTo;
import com.fyself.post.service.post.contract.to.criteria.CommentCriteriaTO;
import com.fyself.post.service.system.contract.to.ResourceCriteriaTO;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javaxt.http.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

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
        return this.get((facade, context) -> facade.uploadImage(part, type, context, false), exchange);
    }

    @ApiSecuredOperation
    @ApiOperation(nickname = "upload_private_file", value = "Upload private file", response = String.class)
    @RequestMapping(path = "/uploadPrivate", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity> uploadPrivateFile(@RequestPart Mono<FilePart> part, @RequestPart String type, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.uploadImage(part, type, context, true), exchange);
    }

    @PostMapping(value = "/get-file", produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiSecuredOperation
    @ApiOperation(nickname = "get_file", value = "Search comments", response = String.class)
    public Mono<ResponseEntity> search(@RequestBody FileTO file, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.getFile(file, context), exchange);
    }

    @PostMapping(value = "/get-file-private", produces = MediaType.APPLICATION_PDF_VALUE)
    @ApiSecuredOperation
    @ApiOperation(nickname = "get_file_private", value = "Get file private", response = String.class)
    public Mono<ResponseEntity> searchPrivate(@RequestBody ResourceCriteriaTO file, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.getFilePrivate(file, context), exchange);
    }

    @PostMapping(value = "/get-url")
    @ApiSecuredOperation
    @ApiOperation(nickname = "get_url", value = "Search Url", response = String.class)
    public Mono<ResponseEntity> searchUrl(@RequestBody UrlTo url, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.getUrl(url, context), exchange);
    }

    @PostMapping(value = "/delete-url-private")
    @ApiSecuredOperation
    @ApiOperation(nickname = "get_url_private", value = "delete private Url", response = Boolean.class)
    public Mono<ResponseEntity> deleteUrlPrivate(@RequestBody ResourceCriteriaTO url, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.deleteUrl(url, context, true), exchange);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPDFFile()
            throws IOException {

        ClassPathResource pdfFile = new ClassPathResource("D://test.pdf");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(pdfFile.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(pdfFile.getInputStream()));
    }

}
