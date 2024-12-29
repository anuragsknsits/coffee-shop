package com.coffeeshop.shop.service;

import com.coffeeshop.shop.entity.Queue;
import com.coffeeshop.shop.model.QueueDetail;

public interface QueueService {
    QueueDetail createQueue(Queue queue);

    void serviceCustomer(Long queueEntryId);
}
