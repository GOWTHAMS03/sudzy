package com.sudzey.sudzey.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "wishlists")
@NoArgsConstructor
@AllArgsConstructor
public class Wishlist {

    @Id
    private String id;
    private String userId;
    private List<String> productId = new ArrayList<>();
}