package com.coffeeshop.shop.entity;

import com.coffeeshop.shop.model.CustomerDetail;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int loyaltyScore;

    public Customer(CustomerDetail customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.loyaltyScore = customer.getLoyaltyScore();
    }
}
