package com.coffeeshop.shop.controller;

import com.coffeeshop.shop.entity.MenuItem;
import com.coffeeshop.shop.model.MenuDetails;
import com.coffeeshop.shop.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/shop/{shopId}")
    public ResponseEntity<MenuDetails> addMenuItem(@PathVariable Long shopId, @RequestBody MenuItem menuItem) {
        return ResponseEntity.ok(menuService.addMenuItem(shopId, menuItem));
    }

    @GetMapping("/shop/{shopId}")
    public ResponseEntity<List<MenuDetails>> getMenuItems(@PathVariable Long shopId) {
        return ResponseEntity.ok(menuService.getMenuItems(shopId));
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long menuItemId) {
        menuService.deleteMenuItem(menuItemId);
        return ResponseEntity.noContent().build();
    }
}
