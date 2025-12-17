package com.example.apigateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {

        /**
         * http://localhost:8080/auth/**           -> http://localhost:8300/api/auth
         * http://localhost:8080/users/**          -> http://localhost:8200/api/users
         * http://localhost:8080/treatments/**         -> http://localhost:8101/api/treatments
         * http://localhost:8080/bookmark/**      -> http://localhost:8102/api/bookmark
         *
         * RouteLocatorBuilder, non blocking, uses Reactor netty, does not use servlet stack
         *
         * Strip prefix: Removes one or more path segments from the request url before forwarding it
         * GET: /api/orders/123
         * StripPrefix=2, removes /api and /orders, forwards /123
         */
        return builder
                .routes()

                .route("auth-service-swagger", r -> r
                        .path("/auth/v3/api-docs")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://auth-service"))

                .route("auth-service", r -> r
                        .path("/auth/**")
                        .filters(f -> f.stripPrefix(0).prefixPath("/api"))
                        .uri("lb://auth-service"))

                .route("userprofile-service-swagger", r -> r
                        .path("/users/v3/api-docs")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://userprofile-service"))

                .route("userprofile-service", r -> r
                        .path("/users/**")
                        .filters(f -> f.stripPrefix(0).prefixPath("/api"))
                        .uri("lb://userprofile-service"))

                .route("treatment-service-swagger", r -> r
                        .path("/treatment/v3/api-docs")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://treatment-service"))

                .route("treatment-service", r -> r
                        .path("/treatments/**")
                        .filters(f -> f.stripPrefix(0).prefixPath("/api"))
                        .uri("lb://treatment-service"))

                .route("bookmark-service-swagger", r -> r
                        .path("/bookmark/v3/api-docs")
                        .filters(f -> f.stripPrefix(1))
                        .uri("lb://bookmark-service"))

                .route("bookmark-service", r -> r
                        .path("/bookmark/**")
                        .filters(f -> f.stripPrefix(0).prefixPath("/api"))
                        .uri("lb://bookmark-service"))

                .build();
    }
}
