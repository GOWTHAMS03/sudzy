package com.sudzey.sudzey.model;


import com.sudzey.sudzey.enumerate.PaymentMethod;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String id;
    private String userId;
    private List<OrderItem> orderItems;
    private double totalPrice;
    private Date orderDate;
    private OrderStatus status;
    private PaymentMethod paymentMethod;
    private LocalDateTime deliveryDate;
    private String trackingNumber;
    private String deliveryPartnerId;
}