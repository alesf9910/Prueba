package com.fyself.post.dist.rest;

import com.fyself.post.facade.PostFacade;
import com.fyself.post.service.post.contract.to.PostTO;
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
 * Post CRUD
 *
 * @author jmmarin
 * @since 0.0.2
 */

@RestController
@RequestMapping("/post")
@Api(tags = "Post", description = "Endpoint for the post management")
public class PostController extends Controller<PostFacade> {

    @PostMapping()
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_create", value = "Create post", response = String.class, code = 201)
    public Mono<ResponseEntity> create(@RequestBody PostTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.create((facade, context) -> facade.create(to, context), exchange);
    }

    @GetMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_load", value = "Load post", response = ResponseEntity.class)
    public Mono<ResponseEntity> load(@PathVariable String id, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.load(id, context), exchange);
    }
}
