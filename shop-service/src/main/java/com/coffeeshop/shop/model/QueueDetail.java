package com.coffeeshop.shop.model;

import com.coffeeshop.shop.entity.Queue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class QueueDetail {
    private Long id;

    private int queueNumber;

    private List<QueueEntryDetail> customers;

    private ShopDetail shop;

    public QueueDetail(Queue queue) {
        this.id = queue.getId();
        this.queueNumber = queue.getQueueNumber();
        if (queue.getCustomers() != null) {
            this.customers = queue.getCustomers().stream().map(QueueEntryDetail::new).toList();
        }
        this.shop = new ShopDetail(queue.getShop());
    }
}
