package com.coffeeshop.shop.service;

import com.coffeeshop.shop.entity.Shop;
import com.coffeeshop.shop.model.ShopDetail;

public interface ShopService {
    ShopDetail setupShop(Shop shop);

    ShopDetail getShop(Long id);
}
