package com.coffeeshop.customer.service;

import com.coffeeshop.customer.model.CoffeeShop;

import java.util.List;

public interface CoffeeShopService {
    List<CoffeeShop> findAllShops();

    CoffeeShop findClosestShop(double latitude, double longitude);
}
