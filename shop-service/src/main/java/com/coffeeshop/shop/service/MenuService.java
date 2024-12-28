package com.coffeeshop.shop.service;

import com.coffeeshop.shop.entity.MenuItem;

import java.util.List;

public interface MenuService {
    MenuItem addMenuItem(Long shopId, MenuItem menuItem);

    List<MenuItem> getMenuItems(Long shopId);

    void deleteMenuItem(Long menuItemId);
}
