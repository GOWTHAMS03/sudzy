package com.sudzey.sudzey.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "offers")
public class Offer {
    @Id
    private String id;
    private String name;
    private double discountPercentage;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<String> applicableProductIds;
}
