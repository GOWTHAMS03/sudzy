package com.sudzey.sudzey.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders_item")
public class OrderItem {
    private String productId;
    private int quantity;
    private double price;
}