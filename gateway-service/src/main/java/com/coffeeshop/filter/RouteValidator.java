package com.coffeeshop.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    AntPathMatcher pathMatcher = new AntPathMatcher();

    public static final List<String> openApiEndpoints = List.of(
            "/auth/register",
            "/auth/login",
            "/auth/logout",
            "/auth/roles",
            "/eureka"
    );

    /*public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints
                    .stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));*/


    private final Map<String, List<String>> rolePathMap = Map.of(
            "/shop/**", List.of("ADMIN"),
            "/user/**", List.of("USER"),
            "/auth/**", List.of("USER, ADMIN")
    );

    public List<String> getRolesForPath(String path) {
        return rolePathMap.entrySet().stream()
                .filter(entry -> pathMatcher.match(entry.getKey(), path))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(List.of());
    }

    public final Predicate<ServerHttpRequest> isSecured = request ->
            rolePathMap.keySet()
                    .stream()
                    .anyMatch(path ->  pathMatcher.match(path, request.getURI().getPath()));

}
