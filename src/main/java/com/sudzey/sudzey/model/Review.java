package com.sudzey.sudzey.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String userId;
    private String productId;
    private String content;
    private int rating;
    private String createdAt;
}