package com.coffeeshop.shop.entity;

import com.coffeeshop.shop.model.ShopDetail;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private String contactDetails;
    private LocalTime openingTime;
    private LocalTime closingTime;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<MenuItem> menu;

    private int numberOfQueues;
    private int maxQueueSize;

    public Shop(@NotNull ShopDetail shop) {
        this.setId(shop.getId());
        this.setName(shop.getName());
        this.setLocation(shop.getLocation());
        this.setContactDetails(shop.getContactDetails());
        this.setOpeningTime(shop.getOpeningTime());
        this.setClosingTime(shop.getClosingTime());
        if (shop.getMenu() != null) {
            this.setMenu(shop.getMenu().stream().map(MenuItem::new).toList());
        }
        this.numberOfQueues = shop.getNumberOfQueues();
        this.maxQueueSize = shop.getMaxQueueSize();
    }
}
