package com.sudzey.sudzey.repository;

import com.sudzey.sudzey.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(String userId);
    Order findByTrackingNumber(String trackingNumber);
}
