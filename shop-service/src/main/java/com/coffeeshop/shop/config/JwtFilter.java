package com.coffeeshop.shop.config;

import com.coffeeshop.shop.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    public JwtFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        Cookie[] cookies = request.getCookies() != null ? request.getCookies() : new Cookie[0];
        String jwt = authHeader != null && authHeader.startsWith("Bearer ") ?
                authHeader.substring(7) :
                Arrays.stream(cookies)
                        .filter(cookie -> "jwt".equals(cookie.getName()))
                        .findFirst().map(Cookie::getValue).orElse(null);
        if (!request.getServletPath().startsWith("/swagger")) {
            try {
                String username = jwtUtil.extractUserName(jwt);
                List<String> roles = jwtUtil.extractClaim(jwt, claims -> claims.get("roles", List.class));

                if (username != null && roles != null) {
                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    // Log successful authentication
                    System.out.println("Authenticated user: " + username + " with roles: " + roles);
                }
            } catch (Exception e) {
                // Log token validation errors
                System.err.println("Invalid JWT token: " + e.getMessage());
            }
        }
        chain.doFilter(request, response);
    }
}

