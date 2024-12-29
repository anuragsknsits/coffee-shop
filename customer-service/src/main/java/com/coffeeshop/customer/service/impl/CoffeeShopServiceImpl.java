package com.coffeeshop.customer.service.impl;

import com.coffeeshop.customer.model.CoffeeShop;
import com.coffeeshop.customer.service.CoffeeShopService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class CoffeeShopServiceImpl implements CoffeeShopService {

    private final RestClient restClient;

    public CoffeeShopServiceImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public List<CoffeeShop> findAllShops() {
        return List.of();
    }

    @Override
    public CoffeeShop findClosestShop(double latitude, double longitude) {
        //TODO: need to design db accordingly.
        return null;
    }
}
