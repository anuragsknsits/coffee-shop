package com.coffeeshop.shop.service.impl;

import com.coffeeshop.shop.entity.Shop;
import com.coffeeshop.shop.model.ShopDetail;
import com.coffeeshop.shop.repository.ShopRepository;
import com.coffeeshop.shop.service.ShopService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public ShopDetail setupShop(Shop shop) {
        return new ShopDetail(shopRepository.save(shop));
    }

    @Override
    public ShopDetail getShop(Long id) {
        Optional<Shop> optionalShop = shopRepository.findById(id);
        return optionalShop.stream().map(ShopDetail::new).findFirst().orElse(null);
    }
}
