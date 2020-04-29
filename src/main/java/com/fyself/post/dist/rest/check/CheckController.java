package com.fyself.post.dist.rest.check;

import com.fyself.post.facade.BasicFacade;
import com.fyself.seedwork.web.Controller;
import com.fyself.seedwork.web.documentation.responses.NoContentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Check
 *
 * @author Yero
 * @since 0.0.2
 */
@RestController
@RequestMapping("/check")
@Api(tags = "Check", description = "Endpoint for the check")
public class CheckController extends Controller<BasicFacade> {
    @GetMapping()
    @ApiOperation(nickname = "check", value = "Check in", response = NoContentResponse.class, code = 200)
    public Mono<ResponseEntity> create(@ApiIgnore ServerWebExchange exchange) {
        return this.get((facade, context) -> facade.ok(), exchange);
    }
}
