package com.coffeeshop.shop.entity;

import com.coffeeshop.shop.model.QueueEntryDetail;
import jakarta.persistence.CascadeType;
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
public class QueueEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Customer customer;

    private String orderDetails;

    public QueueEntry(QueueEntryDetail queueEntry) {
        this.id = queueEntry.getId();
        this.customer = new Customer(queueEntry.getCustomer());
        this.orderDetails = queueEntry.getOrderDetails();
    }
}
