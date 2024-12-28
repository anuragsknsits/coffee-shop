package com.coffeeshop.shop.controller;

import com.coffeeshop.shop.entity.Shop;
import com.coffeeshop.shop.service.ShopService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shops")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public Shop setupShop(@RequestBody Shop shop) {
        return shopService.setupShop(shop);
    }

    @GetMapping("/{id}")
    public Shop getShop(@PathVariable Long id) {
        return shopService.getShop(id);
    }
}
