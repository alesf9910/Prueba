package com.fyself.post.configuration;

import com.fyself.seedwork.security.configuration.DefaultSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authorization.AuthorizationContext;

import static org.springframework.security.config.web.server.SecurityWebFiltersOrder.AUTHENTICATION;
import static reactor.core.publisher.Mono.just;

@Configuration
@EnableWebFluxSecurity
@Import(DefaultSecurityContext.class)
public class SecurityContext {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http.addFilterAt(this.authenticationWebFilter, AUTHENTICATION)
                .exceptionHandling().authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                .csrf()
                .disable()
                .logout()
                .disable()
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                //Swagger setup
                .pathMatchers("/**/*.html").permitAll()
                .pathMatchers("/**/*.js").permitAll()
                .pathMatchers("/i18n/**").permitAll()
                .pathMatchers("/content/**").permitAll()
                .pathMatchers("/test/**").permitAll()
                .pathMatchers(path + "/v2/api-docs/**").permitAll()
                .pathMatchers(path + "/swagger-resources/**").permitAll()
                .pathMatchers(path + "/swagger-ui.html").permitAll()
                .pathMatchers(path + "/csrf/**").permitAll()
                .pathMatchers(path + "/webjars/**").permitAll()
                .pathMatchers(path + "/check**").permitAll()
                //General behavior



                .anyExchange().permitAll()
                .and()
                .build();
    }

    private ReactiveAuthorizationManager<AuthorizationContext> hasSomeRole(String... values) {
        return (authentication, object) -> authentication.filter(Authentication::isAuthenticated)
                .map(ign -> true)
                .switchIfEmpty(just(true))
                .map(AuthorizationDecision::new);
    }


    //<editor-fold desc="Dependency injection">
    private @Autowired
    AuthenticationWebFilter authenticationWebFilter;
    private @Autowired
    ServerAuthenticationEntryPoint authenticationEntryPoint;
    private @Value("/${application.name}")
    String path;
    //</editor-fold>
}
