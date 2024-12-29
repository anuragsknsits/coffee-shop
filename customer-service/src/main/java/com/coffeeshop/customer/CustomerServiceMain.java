package com.coffeeshop.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableJpaRepositories(basePackages = "com.coffeeshop.customer.repository")
public class CustomerServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceMain.class, args);
    }
}