package com.coffeeshop.shop.model;

import com.coffeeshop.shop.entity.MenuItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
public class MenuDetails {
    private Long id;
    private String name;
    private double price;

    public MenuDetails(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public MenuDetails(MenuItem menuItem) {
        this.setId(menuItem.getId());
        this.setName(menuItem.getName());
        this.setPrice(menuItem.getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuDetails that = (MenuDetails) o;
        return Double.compare(price, that.price) == 0 && Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
