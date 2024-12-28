package com.coffeeshop.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.coffeeshop.auth.repository")
public class AuthServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceMain.class, args);
    }
}