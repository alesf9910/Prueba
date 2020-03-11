package com.fyself.post.dist.rest;

import com.fyself.post.facade.PostReportFacade;
import com.fyself.post.service.post.contract.to.CommentTO;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import com.fyself.seedwork.web.documentation.responses.NoContentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for Post Report.
 *
 * @author Alejandro
 * @since 0.0.1
 */
@RestController
@RequestMapping("/post/{post}/report")
@Api(tags = "Comment", description = "Endpoint for the profile shared management")
public class PostReportController extends Controller<PostReportFacade> {
    @PostMapping()
    @ApiSecuredOperation
    @ApiOperation(nickname = "comment_create", value = "Create comment", response = String.class, code = 201)
    public Mono<ResponseEntity> create(@PathVariable String post, @RequestBody PostReportTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.create((facade, context) -> facade.create(to.withPost(post), context), exchange);
    }

    @GetMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "comment_load", value = "Load comment", response = String.class)
    public Mono<ResponseEntity> load(@PathVariable String post, @PathVariable String id, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.load(id, post, context), exchange);
    }

    @PutMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_update", value = "Update comment", response = NoContentResponse.class, code = 204)
    public Mono<ResponseEntity> update(@PathVariable String post, @PathVariable String id, @RequestBody PostReportTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.update(to.withId(id).withPost(post), context), exchange);
    }

    @DeleteMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "comment_load", value = "Update comment", response = NoContentResponse.class)
    public Mono<ResponseEntity> delete(@PathVariable String post, @PathVariable String id, @RequestBody CommentTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.delete(id, post, context), exchange);
    }

    @PostMapping("/search")
    @ApiSecuredOperation
    @ApiOperation(nickname = "comment_search", value = "Search comments", response = SearchResponse.class)
    public Mono<ResponseEntity> search(@PathVariable String post, @RequestBody PostReportCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.search(to, post, context), exchange);
    }


    //<editor-fold desc="Inner classes (Documentation purpose)">
    private static class SearchResponse extends PagedList<PostReportTO> {
    }
    //</editor-fold>
}
