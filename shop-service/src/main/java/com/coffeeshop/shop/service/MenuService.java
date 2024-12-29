package com.coffeeshop.shop.service;

import com.coffeeshop.shop.entity.MenuItem;
import com.coffeeshop.shop.model.MenuDetails;

import java.util.List;

public interface MenuService {
    MenuDetails addMenuItem(Long shopId, MenuItem menuItem);

    List<MenuDetails> getMenuItems(Long shopId);

    void deleteMenuItem(Long menuItemId);
}
