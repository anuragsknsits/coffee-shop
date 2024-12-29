package com.coffeeshop.shop.model;

import com.coffeeshop.shop.entity.QueueEntry;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class QueueEntryDetail {
    private Long id;

    private CustomerDetail customer;

    private String orderDetails;

    public QueueEntryDetail(QueueEntry queueEntry) {
        this.id = queueEntry.getId();
        this.customer = new CustomerDetail(queueEntry.getCustomer());
        this.orderDetails = queueEntry.getOrderDetails();
    }
}
