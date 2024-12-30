package com.coffeeshop.shop.service.impl;

import com.coffeeshop.shop.entity.Customer;
import com.coffeeshop.shop.entity.Queue;
import com.coffeeshop.shop.entity.QueueEntry;
import com.coffeeshop.shop.entity.Shop;
import com.coffeeshop.shop.model.CustomerDetail;
import com.coffeeshop.shop.model.QueueDetail;
import com.coffeeshop.shop.model.QueueEntryDetail;
import com.coffeeshop.shop.repository.CustomerRepository;
import com.coffeeshop.shop.repository.QueueEntryRepository;
import com.coffeeshop.shop.repository.QueueRepository;
import com.coffeeshop.shop.repository.ShopRepository;
import com.coffeeshop.shop.service.QueueService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class QueueServiceImpl implements QueueService {

    private final QueueRepository queueRepository;
    private final QueueEntryRepository queueEntryRepository;
    private final ShopRepository shopRepository;
    private final CustomerRepository customerRepository;

    public QueueServiceImpl(QueueRepository queueRepository, QueueEntryRepository queueEntryRepository, ShopRepository shopRepository, CustomerRepository customerRepository) {
        this.queueRepository = queueRepository;
        this.queueEntryRepository = queueEntryRepository;
        this.shopRepository = shopRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public QueueDetail createQueue(QueueDetail queueDetail) {

        Shop shop = shopRepository.findById(queueDetail.getShop().getId()).orElse(null);
        if (shop == null) {
            throw new RuntimeException("Shop Details Not found");
        }

        List<QueueEntry> queueEntries = queueDetail.getCustomers().stream().map(this::getQueueEntry).collect(Collectors.toList());

        queueEntryRepository.saveAll(queueEntries);

        Queue queue = new Queue(queueDetail);

        return new QueueDetail(queueRepository.save(queue));
    }

    private QueueEntry getQueueEntry(QueueEntryDetail queueEntryDetail) {
        QueueEntry queueEntry = new QueueEntry();
        queueEntry.setId(queueEntryDetail.getId());
        queueEntry.setOrderDetails(queueEntryDetail.getOrderDetails());
        CustomerDetail customerDetail = queueEntryDetail.getCustomer();
        if (customerDetail != null) {
            Customer customer = customerRepository.findById(customerDetail.getId()).orElse(null);
            if (customer == null && customerDetail.getName() != null) {
                log.info("Customer not found for Id : {} So setting new customer for name : {} ", customerDetail.getId(), customerDetail.getName());
                customer = new Customer();
                customer.setName(customerDetail.getName());
                customer.setLoyaltyScore(0);
                customerRepository.save(customer);
            }
            queueEntry.setCustomer(customer);
        }
        if (customerDetail == null) {
            throw new RuntimeException("Invalid request : Add customer information.");
        }
        return queueEntry;
    }

    @Override
    public void serviceCustomer(Long queueEntryId) {
        queueEntryRepository.deleteById(queueEntryId);
    }
}
