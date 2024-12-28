package com.coffeeshop.shop.repository;

import com.coffeeshop.shop.entity.QueueEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueEntryRepository extends JpaRepository<QueueEntry, Long> {
}