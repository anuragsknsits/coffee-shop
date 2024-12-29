package com.coffeeshop.shop.service.impl;

import com.coffeeshop.shop.entity.Queue;
import com.coffeeshop.shop.model.QueueDetail;
import com.coffeeshop.shop.repository.QueueEntryRepository;
import com.coffeeshop.shop.repository.QueueRepository;
import com.coffeeshop.shop.service.QueueService;
import org.springframework.stereotype.Service;

@Service
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final QueueEntryRepository queueEntryRepository;

    public QueueServiceImpl(QueueRepository queueRepository, QueueEntryRepository queueEntryRepository) {
        this.queueRepository = queueRepository;
        this.queueEntryRepository = queueEntryRepository;
    }

    @Override
    public QueueDetail createQueue(QueueDetail queueDetail) {
        Queue queue = new Queue(queueDetail);

        return new QueueDetail(queueRepository.save(queue));
    }

    @Override
    public void serviceCustomer(Long queueEntryId) {
        queueEntryRepository.deleteById(queueEntryId);
    }
}
