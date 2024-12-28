package com.coffeeshop.filter;

import com.coffeeshop.util.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter implements WebFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);
    private final JWTUtil jwtUtil;
    private final RouteValidator validator;

    public JwtAuthFilter(JWTUtil jwtUtil, RouteValidator validator) {
        this.jwtUtil = jwtUtil;
        this.validator = validator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
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

            String authHeader = cookies.getFirst().getValue();
            try {
                String userName = jwtUtil.extractUserName(authHeader);
                List<String> rolesList = jwtUtil.extractClaim(authHeader, claims -> claims.get("roles", List.class));
                List<String> roles = rolesList.stream()
                        .map(role -> role.startsWith("ROLE_") ? role.substring(5) : role) // Normalize roles
                        .collect(Collectors.toList());

                log.debug("Roles extracted from JWT: {}", roles);

                // Validate roles for the requested path
                String path = request.getURI().getPath();
                List<String> requiredRoles = validator.getRolesForPath(path);

                if (requiredRoles.stream().noneMatch(roles::contains)) {
                    log.warn("Access denied. Required roles: {}, but user has roles: {}", requiredRoles, roles);
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
                }

                // Set the authenticated user in the reactive security context
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userName, null, jwtUtil.getAuthorities(roles));
                SecurityContext context = new SecurityContextImpl(authentication);
                log.debug("Authentication token set for user: {}", userName);

                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(context)));
            } catch (Exception e) {
                log.error("JWT validation failed: {}", e.getMessage(), e);
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
            }
        }

        return chain.filter(exchange);
    }
}
