package com.coffeeshop.shop.entity;

import com.coffeeshop.shop.model.MenuDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;

    @ManyToOne
    private Shop shop;

    public MenuItem(MenuDetails menuItem) {
        this.setId(menuItem.getId());
        this.setName(menuItem.getName());
        this.setPrice(menuItem.getPrice());
    }
}
