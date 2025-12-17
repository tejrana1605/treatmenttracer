package com.example.apigateway.filters;

import com.example.apigateway.validators.JwtValidator;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class JwtFilter implements GlobalFilter, Ordered {

    private final JwtValidator jwtValidator;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtFilter(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        var path = exchange.getRequest().getURI().getPath();
        logger.info("path:{}", path);

        // If it is a public path, then skip validation
        if(isPathPublic(path)) {
            return chain.filter(exchange);
        }

        var authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        logger.info("authHeader:{}", authHeader);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization");
        }

        var token =  authHeader.substring(7);
        logger.info("token:{}", token);

        // Mono.fromCallable() -> defers the execution until subscribed
        // Captures all exceptions and turns them into onError()
        // Executed the logic inside
        return Mono.fromCallable(() -> jwtValidator.validateTokenAndGetClaims(token))
                .flatMap(claims -> {
                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                            .header("X-User-Id", claims.getSubject())
                            .header("X-User-Roles", String.valueOf(claims.get("role")))
                            .build();
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());

                })
                .onErrorResume(ex -> unauthorized(exchange, "Invalid or expired token"));
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        var body = """
                {
                    "success": false,
                    "message": "Authentication failed",
                    "error": {
                        "code": "UNAUTHORIZED",
                        "message": %s
                    }
                }
                """.formatted(message);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        // Mono.just() -> wraps an available value and emit it immediately
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    private boolean isPathPublic(String path) {
        return path.startsWith("/auth/login") ||
                path.startsWith("/users/register") ||
                path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/api-docs");
    }


    @Override
    public int getOrder() {
        return -1;
    }
}
