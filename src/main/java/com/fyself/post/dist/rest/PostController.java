package com.fyself.post.dist.rest;

import com.fyself.post.facade.PostFacade;
import com.fyself.post.service.post.contract.to.PostShareTO;
import com.fyself.post.service.post.contract.to.PostTO;
import com.fyself.post.service.post.contract.to.criteria.PostCriteriaTO;
import com.fyself.post.service.post.contract.to.criteria.PostTimelineCriteriaTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import com.fyself.seedwork.web.documentation.responses.NoContentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

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
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "to",
                    required = true,
                    dataTypeClass = PostTO.class,
                    example =
                            "{\n" +
                                    " \"content\":{ " +
                                    " \"link\":\"url\"," +
                                    " \"typeContent\":\"LINK\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"title\": \"string\"," +
                                    " \"description\":\"string\"," +
                                    " \"typeContent\":\"TEXT\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    "\"ask\": \"string\"," +
                                    " \"typeContent\":\"SURVEY_ASK\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"choices\": [" +
                                    "  {\"choice\":\"string\"}," +
                                    "]," +
                                    " \"ask\":\"string\"," +
                                    " \"typeContent\":\"SURVEY_CHOICE\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"options\": [" +
                                    "  {\"option\":\"string\"}," +
                                    "]," +
                                    " \"ask\":\"string\"," +
                                    " \"typeContent\":\"SURVEY_HIERARCHY\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"limits\": [" +
                                    "  {\"string\":\"integer\"}," +
                                    "]," +
                                    " \"ask\":\"string\"," +
                                    " \"typeContent\":\"SURVEY_RATE\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}"

            )
    })
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

    @DeleteMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_load", value = "Update post", response = NoContentResponse.class)
    public Mono<ResponseEntity> delete(@PathVariable String id, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.delete(id, context), exchange);
    }

    @PutMapping("/{id}")
    @ApiSecuredOperation
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "to",
                    required = true,
                    dataTypeClass = PostTO.class,
                    example =
                            "{\n" +
                                    " \"content\":{ " +
                                    " \"link\":\"url\"," +
                                    " \"typeContent\":\"LINK\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"title\": \"string\"," +
                                    " \"description\":\"string\"," +
                                    " \"typeContent\":\"TEXT\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    "\"ask\": \"string\"," +
                                    " \"typeContent\":\"SURVEY_ASK\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"choices\": [" +
                                    "  {\"choice\":\"string\"}," +
                                    "]," +
                                    " \"ask\":\"string\"," +
                                    " \"typeContent\":\"SURVEY_CHOICE\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"options\": [" +
                                    "  {\"option\":\"string\"}," +
                                    "]," +
                                    " \"ask\":\"string\"," +
                                    " \"typeContent\":\"SURVEY_HIERARCHY\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"limits\": [" +
                                    "  {\"string\":\"integer\"}," +
                                    "]," +
                                    " \"ask\":\"string\"," +
                                    " \"typeContent\":\"SURVEY_RATE\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}"

            )
    })
    @ApiOperation(nickname = "post_load", value = "Update post", response = NoContentResponse.class, code = 204)
    public Mono<ResponseEntity> update(@PathVariable String id, @RequestBody PostTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.update(to.withId(id), context), exchange);
    }

    @PatchMapping("/{id}")
    @ApiSecuredOperation
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "to",
                    required = true,
                    dataTypeClass = PostTO.class,
                    example =
                            "{\n" +
                                    " \"content\":{ " +
                                    " \"link\":\"url\"," +
                                    " \"typeContent\":\"LINK\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"title\": \"string\"," +
                                    " \"description\":\"string\"," +
                                    " \"typeContent\":\"TEXT\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    "\"ask\": \"string\"," +
                                    " \"typeContent\":\"SURVEY_ASK\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"choices\": [" +
                                    "  {\"choice\":\"string\"}," +
                                    "]," +
                                    " \"ask\":\"string\"," +
                                    " \"typeContent\":\"SURVEY_CHOICE\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"options\": [" +
                                    "  {\"option\":\"string\"}," +
                                    "]," +
                                    " \"ask\":\"string\"," +
                                    " \"typeContent\":\"SURVEY_HIERARCHY\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}" +
                                    "\n\n" +
                                    "OR" +
                                    "\n\n" +
                                    "{\n" +
                                    " \"content\":{ " +
                                    " \"limits\": [" +
                                    "  {\"string\":\"integer\"}," +
                                    "]," +
                                    " \"ask\":\"string\"," +
                                    " \"typeContent\":\"SURVEY_RATE\"" +
                                    "}," +
                                    " \"access\":\"PUBLIC\"," +
                                    " \"active\":true, " +
                                    "\"blocked\":false," +
                                    " \"urlImage\":\"string\"," +
                                    "}"

            )
    })
    @ApiOperation(nickname = "post_update", value = "Update post", response = NoContentResponse.class, code = 204)
    public Mono<ResponseEntity> patch(@PathVariable String id, @RequestBody HashMap to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.patch(id, to, context), exchange);
    }

    @PostMapping("/search")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_search", value = "Search Posts", response = SearchResponse.class)
    public Mono<ResponseEntity> searchPost(@RequestBody PostCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.search(to, context), exchange);
    }

    @GetMapping("/search")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_search", value = "Search Posts", response = SearchResponse.class)
    public Mono<ResponseEntity> searchGet(PostCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.search(to, context), exchange);
    }

    @GetMapping("/timeline")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_timeline_search", value = "Search Posts that are on the timeline", response = SearchResponse.class)
    public Mono<ResponseEntity> searchPostTimelineGet(PostTimelineCriteriaTO criteria, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchPostTimeline(criteria, context), exchange);
    }

    @PostMapping("/timeline")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_timeline_search", value = "Search Posts that are on the timeline", response = SearchResponse.class)
    public Mono<ResponseEntity> searchPostTimeline(@RequestBody PostTimelineCriteriaTO criteria, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchPostTimeline(criteria, context), exchange);
    }

    @PostMapping("/{id}/share-with")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_share_with", value = "Share post with user", response = NoContentResponse.class, code = 204)
    public Mono<ResponseEntity> shareWith(@PathVariable String id, @RequestBody PostShareTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.shareWith(to.withId(id), context), exchange);
    }

    @PostMapping("/{id}/stop-share-with")
    @ApiSecuredOperation
    @ApiOperation(nickname = "post_stop_share_with", value = "Stop share post with user", response = NoContentResponse.class, code = 204)
    public Mono<ResponseEntity> stopShareWith(@PathVariable String id, @RequestBody PostShareTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.stopShareWith(to.withId(id), context), exchange);
    }

    //<editor-fold desc="Inner classes (Documentation purpose)">
    private static class SearchResponse extends PagedList<PostTO> {
    }
//</editor-fold>
}
