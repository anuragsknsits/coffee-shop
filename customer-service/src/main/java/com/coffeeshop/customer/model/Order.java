package com.coffeeshop.customer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
    private Long id;

    private String coffeeName;
    private int quantity;
    private double price;

    private Customer customer;

    private String status; // PENDING, IN_QUEUE, SERVED
    private int positionInQueue;
    private int expectedWaitTime;
}