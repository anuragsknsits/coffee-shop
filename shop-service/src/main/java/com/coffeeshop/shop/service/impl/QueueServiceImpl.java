package com.coffeeshop.shop.service.impl;

import com.coffeeshop.shop.entity.Customer;
import com.coffeeshop.shop.entity.MenuItem;
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
        List<QueueEntry> queueEntries = queueDetail.getCustomers().stream()
                .map(queueEntryDetail -> getQueueEntry(queueEntryDetail, shop.getMenu()))
                .collect(Collectors.toList());

        queueEntryRepository.saveAll(queueEntries);

        Queue queue = queueRepository.save(new Queue(queueDetail));
        queue.setShop(shop);
        return new QueueDetail(queue);
    }

    private QueueEntry getQueueEntry(QueueEntryDetail queueEntryDetail, List<MenuItem> menu) {
        QueueEntry queueEntry = new QueueEntry();
        queueEntry.setId(queueEntryDetail.getId());
        // Check if the order exists in the menu (case-insensitive)
        boolean isOrderAvailable = menu.stream()
                .anyMatch(menuItem -> menuItem.getName().equalsIgnoreCase(queueEntryDetail.getOrderDetails()));

        if (!isOrderAvailable) {
            throw new RuntimeException("Order not available: " + queueEntryDetail.getOrderDetails());
        }
        queueEntry.setOrderDetails(queueEntryDetail.getOrderDetails());
        CustomerDetail customerDetail = queueEntryDetail.getCustomer();
        if (customerDetail == null) {
            throw new RuntimeException("Invalid request : Add customer information.");
        }
        queueEntry.setCustomer(getCustomer(customerDetail));
        return queueEntry;
    }

    private Customer getCustomer(CustomerDetail customerDetail) {
        Customer customer = null;

        // Try to fetch customer by ID if available
        if (customerDetail.getId() != null) {
            customer = customerRepository.findById(customerDetail.getId()).orElse(null);
        }

        // If customer not found, create a new one if a name is provided
        if (customer == null) {
            if (customerDetail.getName() == null || customerDetail.getName().isBlank()) {
                throw new RuntimeException("Customer name is required to create a new customer");
            }

            log.info("Customer not found for Id: {}. Creating new customer with name: {}",
                    customerDetail.getId(), customerDetail.getName());

            customer = new Customer();
            customer.setName(customerDetail.getName());
            customer.setLoyaltyScore(0);
        } else {
            // If customer exists, increment the loyalty score
            customer.setLoyaltyScore(customer.getLoyaltyScore() + 1);
        }

        // Save the customer (either newly created or updated)
        customer = customerRepository.save(customer);

        return customer;
    }


    @Override
    public void serviceCustomer(Long queueEntryId) {
        queueEntryRepository.deleteById(queueEntryId);
    }
}
