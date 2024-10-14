package com.sudzey.sudzey.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "coupons")
public class Coupon {
    @Id
    private String id;
    private String code;
    private String description;
    private double discountAmount;
    private boolean percentage;
    private LocalDateTime validFrom;
    private LocalDateTime validUntil;
    private int maxUsage;
    private int currentUsage;
    private int maxUsagePerUser;
}
