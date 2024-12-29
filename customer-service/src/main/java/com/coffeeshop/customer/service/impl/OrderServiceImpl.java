package com.coffeeshop.customer.service.impl;

import com.coffeeshop.customer.model.Customer;
import com.coffeeshop.customer.model.Order;
import com.coffeeshop.customer.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order placeOrder(Order order) {
        order.setStatus("PENDING");
        //TODO: Save it into db with database status
        //orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> getOrdersByCustomer(Long customerId) {
        Customer customer = Customer.builder()
                .id(1L)
                .name("vikas")
                .email("vikas@gmail.com")
                .build();

        List<Order> orders = List.of(Order.builder()
                .customer(customer)
                .coffeeName("latte")
                .price(5.5)
                .positionInQueue(2)
                .quantity(1)
                .build());
        //Todo: find all order from db or fetch order for that customer.
        //List<Order> orders = orderRepository.findAll()
        return orders.stream()
                .filter(order -> order.getCustomer().getId().equals(customerId))
                .toList();
    }

    @Override
    public void exitQueue(Long orderId) {
        //Todo: orderRepository.deleteById(orderId);
    }
}
