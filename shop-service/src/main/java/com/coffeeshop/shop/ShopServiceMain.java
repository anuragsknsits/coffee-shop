package com.coffeeshop.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.coffeeshop.shop.repository")
public class ShopServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(ShopServiceMain.class, args);
    }
}