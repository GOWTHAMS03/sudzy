package com.sudzey.sudzey.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "categories")
public class Category {

    @Id
    private String id;
    private String name; // Category name
    private String description; // Optional description of the category
}
