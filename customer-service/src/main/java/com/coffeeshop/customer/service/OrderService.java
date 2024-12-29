package com.coffeeshop.customer.service;

import com.coffeeshop.customer.model.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);

    List<Order> getOrdersByCustomer(Long customerId);

    void exitQueue(Long orderId);
}
