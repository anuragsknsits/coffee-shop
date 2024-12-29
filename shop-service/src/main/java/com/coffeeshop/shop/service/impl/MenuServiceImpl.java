package com.coffeeshop.shop.service.impl;

import com.coffeeshop.shop.entity.MenuItem;
import com.coffeeshop.shop.entity.Shop;
import com.coffeeshop.shop.model.MenuDetails;
import com.coffeeshop.shop.repository.MenuItemRepository;
import com.coffeeshop.shop.repository.ShopRepository;
import com.coffeeshop.shop.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuItemRepository menuItemRepository;
    private final ShopRepository shopRepository;

    public MenuServiceImpl(MenuItemRepository menuItemRepository, ShopRepository shopRepository) {
        this.menuItemRepository = menuItemRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public MenuDetails addMenuItem(Long shopId, MenuItem menuItem) {
        Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new RuntimeException("Shop not found"));
        menuItem.setShop(shop);
        MenuItem menu = menuItemRepository.save(menuItem);
        return new MenuDetails(menu);
    }

    @Override
    public List<MenuDetails> getMenuItems(Long shopId) {
        List<MenuItem> menuItems = menuItemRepository.findAll().stream()
                .filter(menuItem -> menuItem.getShop().getId().equals(shopId))
                .toList();
        return menuItems.stream().map(MenuDetails::new).toList();
    }

    @Override
    public void deleteMenuItem(Long menuItemId) {
        menuItemRepository.deleteById(menuItemId);
    }
}
