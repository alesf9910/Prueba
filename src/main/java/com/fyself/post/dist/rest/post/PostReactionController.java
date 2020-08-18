package com.fyself.post.dist.rest.post;

import com.fyself.post.facade.PostReactionFacade;
import com.fyself.post.facade.PostReportFacade;
import com.fyself.post.service.post.contract.to.PostReportTO;
import com.fyself.post.service.post.contract.to.ReactionTO;
import com.fyself.post.service.post.contract.to.criteria.PostReportCriteriaTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import com.fyself.seedwork.web.documentation.responses.NoContentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for Post Report.
 *
 * @author Yero
 * @since 0.0.1
 */
@RestController
@RequestMapping("/reaction")
@Api(tags = "Post reaction", description = "Endpoint for reaction a post")
public class PostReactionController extends Controller<PostReactionFacade> {

    @PostMapping()
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_reaction_create", value = "Create post reaction", response = String.class, code = 201)
    public Mono<ResponseEntity> create(@ApiParam(name = "to", value = "Data of reaction to be create", required = true) @RequestBody ReactionTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.create((facade, context) -> facade.create(to, context), exchange);
    }

    @GetMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_reaction_load", value = "Load post reaction", response = GetResponse.class)
    public Mono<ResponseEntity> load(@ApiParam(name = "id", value = "ID of reaction to be load", required = true) @PathVariable String id, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.load(id, context), exchange);
    }

    @PutMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_reaction_update", value = "Update post reaction", response = NoContentResponse.class, code = 204)
    public Mono<ResponseEntity> update(@ApiParam(name = "id", value = "ID of reaction to be reactioned", required = true) @PathVariable String id,
                                       @ApiParam(name = "id", value = "Post reaction data to be update", required = true) @RequestBody PostReportTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.update(to.withReportId(id), context), exchange);
    }

    @DeleteMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_reaction_delete", value = "Delete post reaction", response = NoContentResponse.class)
    public Mono<ResponseEntity> delete(@ApiParam(name = "post", value = "ID of reaction to be deleted", required = true) @PathVariable String id, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.delete(id, context), exchange);
    }

    @PostMapping("/search")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_reaction_search_get", value = "Search post reaction", response = SearchResponse.class)
    public Mono<ResponseEntity> searchGet(@ApiParam(name = "to", value = "Data of post reaction to be search", required = true) PostReportCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.search(to, context), exchange);
    }

    //<editor-fold desc="Inner classes (Documentation purpose)">
    private static class SearchResponse extends PagedList<ReactionTO> {}
    private static class GetResponse extends ReactionTO {}
    //</editor-fold>
}
