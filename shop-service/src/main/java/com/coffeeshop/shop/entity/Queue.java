package com.coffeeshop.shop.entity;

import com.coffeeshop.shop.model.QueueDetail;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Queue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int queueNumber;

    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<QueueEntry> customers;

    @ManyToOne
    private Shop shop;

    public Queue(QueueDetail queue) {
        this.id = queue.getId();
        this.queueNumber = queue.getQueueNumber();
        if (queue.getCustomers() != null) {
            this.customers = queue.getCustomers().stream().map(QueueEntry::new).toList();
        }
        this.shop = new Shop(queue.getShop());
    }
}
