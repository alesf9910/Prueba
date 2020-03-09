package com.fyself.post.dist.rest;

import com.fyself.post.facade.CommentFacade;
import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Comment CRUD
 *
 * @author jmmarin
 * @since 0.0.2
 */
@RestController
@RequestMapping("/post/{post}/comment")
@Api(tags = "Comment", description = "Endpoint for the profile shared management")
public class CommentController extends Controller<CommentFacade> {

    @PostMapping()
    @ApiSecuredOperation
    @ApiOperation(nickname = "comment_create", value = "Create comment", response = String.class)
    public Mono<ResponseEntity> loadSharedWithMe(@PathVariable String post, @RequestBody CommentTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.create((facade, context) -> facade.create(to.withPost(post), context), exchange);
    }

    //<editor-fold desc="Inner classes (Documentation purpose)">
    private static class SearchResponse extends PagedList<CommentTO> {
    }
    //</editor-fold>

}
