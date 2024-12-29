package com.coffeeshop.shop.model;

import com.coffeeshop.shop.entity.Customer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class CustomerDetail {
    private Long id;
    private String name;
    private int loyaltyScore;

    public CustomerDetail(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.loyaltyScore = customer.getLoyaltyScore();
    }
}
