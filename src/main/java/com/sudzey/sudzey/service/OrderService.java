package com.sudzey.sudzey.service;

import com.sudzey.sudzey.dto.OrderDTO;
import com.sudzey.sudzey.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Order placeOrder(OrderDTO orderDTO);
    Order updateOrderStatus(String orderId, String status);
    Order getOrderById(String orderId);
    Page<Order> getAllOrders(Pageable pageable);
    List<Order> getOrdersByUserId(String userId);
}
