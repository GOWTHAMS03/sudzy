package com.sudzey.sudzey.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Data
@Document(collection = "carts")
public class Cart {

    @Id
    private String id;
    private String userId; // User's ID
    private Map<String, Integer> products = new HashMap<>(); // Product ID and quantity
}
