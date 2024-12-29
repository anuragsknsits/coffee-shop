package com.coffeeshop.shop.controller;

import com.coffeeshop.shop.entity.Queue;
import com.coffeeshop.shop.model.QueueDetail;
import com.coffeeshop.shop.service.QueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/queues")
public class QueueController {

    private final QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping
    public ResponseEntity<QueueDetail> createQueue(@RequestBody Queue queue) {
        return ResponseEntity.ok(queueService.createQueue(queue));
    }

    @DeleteMapping("/service/{queueEntryId}")
    public void serviceCustomer(@PathVariable Long queueEntryId) {
        queueService.serviceCustomer(queueEntryId);
    }
}
