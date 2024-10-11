package com.sudzey.sudzey.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "wishlists")
public class Wishlist {

    @Id
    private String id;
    private String userId; // User's ID
    private List<String> productIds; // List of product IDs added to the wishlist
}