package com.fyself.post.dist.rest;

import com.fyself.post.facade.PostReportFacade;
import com.fyself.post.service.post.contract.to.PostReportTO;
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
 * @author Alejandro
 * @since 0.0.1
 */
@RestController
@RequestMapping("/report")
@Api(tags = "PostReports", description = "Endpoint for report a post")
public class PostReportController extends Controller<PostReportFacade> {

    @PostMapping()
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_create", value = "Create post reports", response = String.class, code = 201)
    public Mono<ResponseEntity> create(@ApiParam(name = "to", value = "Data of report to be report", required = true) @RequestBody PostReportTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.create((facade, context) -> facade.create(to, context), exchange);
    }

    @GetMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_load", value = "Load post reports", response = PostReportResponse.class)
    public Mono<ResponseEntity> load(@ApiParam(name = "id", value = "ID of report to be load", required = true) @PathVariable String id, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.load(id, context), exchange);
    }

    @PutMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_update", value = "Update post reports", response = NoContentResponse.class, code = 204)
    public Mono<ResponseEntity> update(@ApiParam(name = "id", value = "ID of report to be reported", required = true) @PathVariable String id, @RequestBody PostReportTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.update(to.withReportId(id), context), exchange);
    }

    @DeleteMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_delete", value = "Delete post reports", response = NoContentResponse.class)
    public Mono<ResponseEntity> delete(@ApiParam(name = "post", value = "ID of report to be deleted", required = true) @PathVariable String id, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.delete(id, context), exchange);
    }

    @GetMapping()
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_search_get", value = "Search post reports", response = SearchResponse.class)
    public Mono<ResponseEntity> searchGet(PostReportCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.search(to, context), exchange);
    }

    @PostMapping("/all")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_search_post", value = "Search post reports", response = SearchResponse.class)
    public Mono<ResponseEntity> searchPost(@ApiParam(name = "to", value = "Data of post report to be search", required = true) @RequestBody PostReportCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.search(to, context), exchange);
    }

    @GetMapping("/tome")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_to_me_search_get", value = "Search post reports made for me", response = SearchResponse.class)
    public Mono<ResponseEntity> searchByUserGet(PostReportCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchToMe(to, context), exchange);
    }

    @PostMapping("/tome/all")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_to_me_search_post", value = "Search post reports made for me", response = SearchResponse.class)
    public Mono<ResponseEntity> searchByUserPost(@ApiParam(name = "to", value = "Data of post report to be search", required = true) @RequestBody PostReportCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchToMe(to, context), exchange);
    }

    @GetMapping("/me")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_from_me_search_get", value = "Search post reports made by me", response = SearchResponse.class)
    public Mono<ResponseEntity> searchByMeGet(PostReportCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchByMe(to, context), exchange);
    }

    @PostMapping("/me/all")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_report_from_me_search_post", value = "Search post reports made by me", response = SearchResponse.class)
    public Mono<ResponseEntity> searchByMePost(@ApiParam(name = "to", value = "Data of post report to be search", required = true) @RequestBody PostReportCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchByMe(to, context), exchange);
    }

    //<editor-fold desc="Inner classes (Documentation purpose)">
    private static class SearchResponse extends PagedList<PostReportTO> {
    }

    private static class PostReportResponse extends PostReportTO {
    }
    //</editor-fold>
}
