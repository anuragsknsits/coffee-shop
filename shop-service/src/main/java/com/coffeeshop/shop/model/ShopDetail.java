package com.coffeeshop.shop.model;

import com.coffeeshop.shop.entity.MenuItem;
import lombok.Builder;

import java.time.LocalTime;
import java.util.List;

@Builder
public class ShopDetail {
    private Long id;

    private String name;
    private String location;
    private String contactDetails;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private List<MenuItem> menu;

    private int numberOfQueues;
    private int maxQueueSize;
}
