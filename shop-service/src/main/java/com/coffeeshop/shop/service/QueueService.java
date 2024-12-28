package com.coffeeshop.shop.service;

import com.coffeeshop.shop.entity.Queue;

public interface QueueService {
    Queue createQueue(Queue queue);

    void serviceCustomer(Long queueEntryId);
}
