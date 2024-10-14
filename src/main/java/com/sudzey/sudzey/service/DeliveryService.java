package com.sudzey.sudzey.service;

import com.sudzey.sudzey.model.Order;

public interface DeliveryService {
    String createDelivery(Order order);
    Order getDeliveryStatus(String trackingNumber);
}
