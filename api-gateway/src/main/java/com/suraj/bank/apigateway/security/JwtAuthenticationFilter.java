package com.suraj.bank.apigateway.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import reactor.core.publisher.Mono;

//JwtAuthenticationFilter in API Gateway
//
//This filter will:
//
//Intercept every incoming request.
//Allow public endpoints (/auth/login, /auth/register, /auth/refresh, /auth/logout).
//Extract the Authorization: Bearer <token> header.
//Validate the JWT.
//Reject invalid or missing tokens.
//Forward valid requests to downstream services.

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Public endpoints
        if (path.startsWith("/auth/login")
                || path.startsWith("/auth/register")
                || path.startsWith("/auth/refresh")
                || path.startsWith("/auth/logout")
                || path.startsWith("/actuator")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        if (!jwtService.isTokenValid(token)) {
            exchange.getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        // Optional: extract email and forward as header
        String email = jwtService.extractEmail(token);

        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(builder -> builder.header("X-User-Email", email))
                .build();

        return chain.filter(mutatedExchange);
    }
}