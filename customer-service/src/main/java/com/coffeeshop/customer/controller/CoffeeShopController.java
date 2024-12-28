package com.coffeeshop.customer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeShopController {

    @GetMapping("/nearby")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> getNearByShop() {
        return ResponseEntity.ok("Near the Chhatarpur");
    }
}
