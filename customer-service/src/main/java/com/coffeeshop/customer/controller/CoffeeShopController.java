package com.coffeeshop.customer.controller;

import com.coffeeshop.customer.model.CoffeeShop;
import com.coffeeshop.customer.service.CoffeeShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coffeeshop")
public class CoffeeShopController {

    private final CoffeeShopService coffeeShopService;

    public CoffeeShopController(CoffeeShopService coffeeShopService) {
        this.coffeeShopService = coffeeShopService;
    }

    @GetMapping
    public ResponseEntity<List<CoffeeShop>> getAllShops() {
        return ResponseEntity.ok(coffeeShopService.findAllShops());
    }

    @GetMapping("/closest")
    public ResponseEntity<CoffeeShop> getClosestShop(@RequestParam double latitude, @RequestParam double longitude) {
        return ResponseEntity.ok(coffeeShopService.findClosestShop(latitude, longitude));
    }
}
