package com.sudzey.sudzey.model;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Document(collection = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @Min(value = 0, message = "Price must be non-negative")
    private double price;

    private boolean stock;
    private int quantity;

    private List<String> images;

    @DBRef
    private Category category;

    private int salesCount;
    private int views;
    private boolean isTrending;
    private boolean isBestseller;

    private List<String> reviewIds;
    private List<String> offerIds;
}
