package com.coffeeshop.shop.service.impl;

import com.coffeeshop.shop.entity.Shop;
import com.coffeeshop.shop.repository.ShopRepository;
import com.coffeeshop.shop.service.ShopService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop setupShop(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public Shop getShop(Long id) {
        Optional<Shop> optionalShop = shopRepository.findById(id);
        optionalShop.ifPresent(shop -> Hibernate.initialize(shop.getMenu()));
        return optionalShop.orElse(null);
    }
}
