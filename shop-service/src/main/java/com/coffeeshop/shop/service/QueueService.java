package com.coffeeshop.shop.service;

import com.coffeeshop.shop.model.QueueDetail;

public interface QueueService {
    QueueDetail createQueue(QueueDetail queue);

    void serviceCustomer(Long queueEntryId);
}
