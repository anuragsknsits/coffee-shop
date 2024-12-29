package com.coffeeshop.shop.model;

import com.coffeeshop.shop.entity.Shop;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class ShopDetail {
    private Long id;
    private String name;
    private String location;
    private String contactDetails;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private List<MenuDetails> menu;
    private int numberOfQueues;
    private int maxQueueSize;

    public ShopDetail(@NotNull Shop shop) {
        this.setId(shop.getId());
        this.setName(shop.getName());
        this.setLocation(shop.getLocation());
        this.setContactDetails(shop.getContactDetails());
        this.setOpeningTime(shop.getOpeningTime());
        this.setClosingTime(shop.getClosingTime());
        if (shop.getMenu() != null) {
            this.setMenu(shop.getMenu().stream().map(MenuDetails::new).toList());
        }
        this.numberOfQueues = shop.getNumberOfQueues();
        this.maxQueueSize = shop.getMaxQueueSize();
    }
}
