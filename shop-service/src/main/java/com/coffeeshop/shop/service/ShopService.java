package com.coffeeshop.shop.service;

import com.coffeeshop.shop.entity.Shop;

public interface ShopService {
    Shop setupShop(Shop shop);

    Shop getShop(Long id);
}
