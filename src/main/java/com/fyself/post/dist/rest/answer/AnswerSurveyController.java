package com.fyself.post.dist.rest.answer;

import com.fyself.post.facade.AnswerSurveyFacade;
import com.fyself.post.service.post.contract.to.AnswerSurveyTO;
import com.fyself.post.service.post.contract.to.AnswerTO;
import com.fyself.post.service.post.contract.to.criteria.AnswerSurveyCriteriaTO;
import com.fyself.seedwork.service.PagedList;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.annotations.ApiSecuredOperation;
import com.fyself.seedwork.web.documentation.responses.NoContentResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;

/**
 * Controller for Answer.
 *
 * @author Alejandro
 * @since 0.0.1
 */
@RestController
@RequestMapping("/answer")
@Api(tags = "Answer", description = "Endpoint to make answer")
public class AnswerSurveyController extends Controller<AnswerSurveyFacade> {

    @PostMapping()
    @ApiSecuredOperation
    @ApiOperation(
            nickname = "answer_survey_create", value = "Create answer", response = String.class, code = 201
    )
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "to",
                    required = true,
                    dataTypeClass = AnswerSurveyTO.class,
                    example =
                            "SURVEY_CHOICE\n" +
                                    "{\n" +
                                    "\t\"post\" : \"5e418dc8a39dd769595cd042\",\n" +
                                    "\t\"answer\" : {\n" +
                                    "\t\t\"type\": \"SURVEY_CHOICE\",\n" +
                                    "\t\t\"answer\": [\n" +
                                    "\t\t\t\"id-option-1\": \n" +
                                    "\t\t\t\"id-option-2\": \n" +
                                    "\t\t\t\"id-option-n\": \n" +
                                    "\t\t]\n" +
                                    "\t}\n" +
                                    "}\n" +
                                    "\n" +
                                    "SURVEY_ASK\n" +
                                    "{\n" +
                                    "\t\"post\" : \"5e418dc8a39dd769595cd042\",\n" +
                                    "\t\"answer\" : {\n" +
                                    "\t\t\"type\": \"SURVEY_ASK\",\n" +
                                    "\t\t\"answer\": \"TEXT\"\n" +
                                    "\t}\n" +
                                    "}\n" +
                                    "\n" +
                                    "SURVEY_HIERARCHY\n" +
                                    "{\n" +
                                    "\t\"post\" : \"5e418dc8a39dd769595cd042\",\n" +
                                    "\t\"answer\" : {\n" +
                                    "\t\t\"type\": \"SURVEY_HIERARCHY\",\n" +
                                    "\t\t\"answer\": {\n" +
                                    "\t\t\t\"id-option-1\": 3,\n" +
                                    "\t\t\t\"id-option-2\": 6\n" +
                                    "\t\t\t\"id-option-n\": n\n" +
                                    "\t\t}\n" +
                                    "\t}\n" +
                                    "}\n" +
                                    "\n" +
                                    "SURVEY_RATE\n" +
                                    "{\n" +
                                    "\t\"post\" : \"5e418dc8a39dd769595cd042\",\n" +
                                    "\t\"answer\" : {\n" +
                                    "\t\t\"type\": \"SURVEY_RATE\",\n" +
                                    "\t\t\"answer\": 1\n" +
                                    "\t}\n" +
                                    "}\n"

            )
    })
    public Mono<ResponseEntity> create(@ApiParam(name = "to", value = "Data answer to be create", required = true) @RequestBody AnswerSurveyTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.create((facade, context) -> facade.create(to, context), exchange);
    }

    @GetMapping("/{id}/{postId}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_load", value = "Load answer", response = AnswerResponse.class)
    public Mono<ResponseEntity> load(@ApiParam(name = "id", value = "ID of answer to be load", required = true) @PathVariable String id,
                                     @ApiParam(name = "postId", value = "ID of post to load the answer", required = true) @PathVariable String postId, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.load(id, postId, context), exchange);
    }

    @PutMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_update", value = "Update answer reports", response = NoContentResponse.class, code = 204)
    public Mono<ResponseEntity> update(@ApiParam(name = "id", value = "ID of answer to be answered", required = true) @PathVariable String id,
                                       @ApiParam(name = "to", value = "Answer data to be update", required = true) @RequestBody AnswerSurveyTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.update(to.withAnswerId(id), context), exchange);
    }

    @PatchMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_patch", value = "Update answer survey", response = NoContentResponse.class, code = 204)
    public Mono<ResponseEntity> patch(@ApiParam(name = "id", value = "ID of answer to be patch", required = true) @PathVariable String id,
                                      @ApiParam(name = "to", value = "Answer type to be patch", required = true) @RequestBody HashMap to, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.patch(id, to, context), exchange);
    }

    @DeleteMapping("/{id}")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_delete", value = "Delete answer", response = NoContentResponse.class)
    public Mono<ResponseEntity> delete(@ApiParam(name = "id", value = "ID of answer to be deleted", required = true) @PathVariable String id, @ApiIgnore ServerWebExchange exchange) {
        return this.perform((facade, context) -> facade.delete(id, context), exchange);
    }

    @GetMapping()
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_search_get", value = "Search answer", response = AnswerResponse.class)
    public Mono<ResponseEntity> searchGet(@ApiParam(name = "to", value = "Data of answer to be search", required = true) AnswerSurveyCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.search(to, context), exchange);
    }

    @PostMapping("/all")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_search_post", value = "Search answer", response = SearchResponse.class)
    public Mono<ResponseEntity> searchPost(@ApiParam(name = "to", value = "Data of answer report to be search", required = true) @RequestBody AnswerSurveyCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.search(to, context), exchange);
    }


    @GetMapping("/sync")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_search_sync", value = "Search answer", response = NoContentResponse.class)
    public Mono<ResponseEntity> searchPost(@ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.sync(context), exchange);
    }


    @GetMapping("/tome")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_to_me_search_get", value = "Search answer made for me", response = SearchResponse.class)
    public Mono<ResponseEntity> searchByUserGet(@ApiParam(name = "to", value = "Data of answer report to be search") AnswerSurveyCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchToMe(to, context), exchange);
    }

    @PostMapping("/tome/all")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_to_me_search_post", value = "Search answer made for me", response = SearchResponse.class)
    public Mono<ResponseEntity> searchByUserPost(@ApiParam(name = "to", value = "Data of answer report to be search") @RequestBody AnswerSurveyCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchToMe(to, context), exchange);
    }

    @GetMapping("/me")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_from_me_search_get", value = "Search answer made by me", response = SearchResponse.class)
    public Mono<ResponseEntity> searchByMeGet(@ApiParam(name = "to", value = "Data of answer report to be search") AnswerSurveyCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchByMe(to, context), exchange);
    }

    @PostMapping("/me/all")
    @ApiSecuredOperation
    @ApiOperation(nickname = "answer_survey_from_me_search_post", value = "Search answer made by me", response = SearchResponse.class)
    public Mono<ResponseEntity> searchByMePost(@ApiParam(name = "to", value = "Data of answer to be search") @RequestBody AnswerSurveyCriteriaTO to, @ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.searchByMe(to, context), exchange);
    }

    //<editor-fold desc="Inner classes (Documentation purpose)">
    private static class SearchResponse extends PagedList<AnswerSurveyTO> {
    }

    private static class AnswerResponse extends AnswerSurveyTO {
        public AnswerResponse(String post, AnswerTO answer) {
            super(post, answer);
        }
    }
}
