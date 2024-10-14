package com.sudzey.sudzey.dto;

import com.sudzey.sudzey.model.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;
    private double price;
    private boolean stock;
    private int quantity;
    private List<String> images;
    private Category category;
    private int salesCount;
    private int views;
    private boolean isTrending;
    private boolean isBestseller;

}

