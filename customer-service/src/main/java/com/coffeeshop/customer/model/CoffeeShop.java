package com.coffeeshop.customer.model;

import lombok.Data;

@Data
public class CoffeeShop {
    private Long id;

    private String name;
    private String location;
    private String contactDetails;

    private double latitude;
    private double longitude;
}
