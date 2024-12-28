package com.coffeeshop.auth.controller;

import com.coffeeshop.auth.model.AuthResponse;
import com.coffeeshop.auth.model.AuthenticationRequest;
import com.coffeeshop.auth.model.SignUp;
import com.coffeeshop.auth.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody SignUp registerRequest) {
        try {
            userService.registerUser(registerRequest);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        try {
            String token = userService.authenticateUser(authenticationRequest);
            Cookie jwtCookie = new Cookie("jwt", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(jwtCookie);
            return ResponseEntity.ok(new AuthResponse(authenticationRequest.getEmailId(), token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(null);
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // Invalidate JWT Token (optional: depends on your strategy)
        // Clear JWT cookie
        for (Cookie jwtCookie : request.getCookies()) {
            jwtCookie.setValue(null);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(0); // Delete the cookie
            response.addCookie(jwtCookie);
        }
        // Invalidate session (if applicable)
        request.getSession().invalidate();

        return "Logged out successfully";
    }

    @GetMapping("/verifyToken")
    public String verifyToken(HttpServletRequest request) {
        Optional<Cookie> optionalCookie = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("jwt")).findFirst();
        String token = optionalCookie.map(Cookie::getValue).orElse(null);
        return userService.getUserName(token);
    }
}
