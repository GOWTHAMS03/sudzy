package com.sudzey.sudzey.controller;

import com.sudzey.sudzey.model.Order;
import com.sudzey.sudzey.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingController {

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("/{trackingNumber}")
    public ResponseEntity<Order> getOrderByTrackingNumber(@PathVariable String trackingNumber) {
        Order order = orderRepository.findByTrackingNumber(trackingNumber);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order);
    }


}
