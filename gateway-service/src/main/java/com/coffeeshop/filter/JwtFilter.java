package com.coffeeshop.filter;

import com.coffeeshop.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component

public class JwtFilter extends AbstractGatewayFilterFactory<JwtFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);
    private final JWTUtil jwtUtil;
    private final RouteValidator validator;

    public JwtFilter(JWTUtil jwtUtil, RouteValidator validator) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
        this.validator = validator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("Processing request: {}", request.getPath());

            if (validator.isSecured.test(request)) {
                log.debug("Request is secured, validating JWT token...");

                // Extract JWT from cookies
                List<HttpCookie> cookies = request.getCookies().get("jwt");
                if (cookies == null || cookies.isEmpty()) {
                    log.warn("Missing JWT cookie in the request.");
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing JWT cookie");
                }

                /*if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authorization header");
                }
                String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }*/
                String authHeader = cookies.getFirst().getValue();
                try {
                    jwtUtil.validateToken(authHeader);
                    List<String> roles = jwtUtil.extractClaim(authHeader, claims -> claims.get("roles", List.class));

                    log.debug("Roles extracted from JWT: {}", roles);

                    // Validate roles for the requested path
                    String path = request.getURI().getPath();
                    List<String> requiredRoles = validator.getRolesForPath(path);

                    if (requiredRoles.stream().noneMatch(roles::contains)) {
                        log.warn("Access denied. Required roles: {}, but user has roles: {}", requiredRoles, roles);
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
                    }
                } catch (Exception e) {
                    log.error("JWT validation failed: {}", e.getMessage(), e);
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
                }
            }

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}
