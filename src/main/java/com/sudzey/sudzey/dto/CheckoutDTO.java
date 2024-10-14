package com.sudzey.sudzey.dto;

import lombok.Data;

@Data
public class CheckoutDTO {
    private String userId;
    private double totalAmount;
    private String couponCode;
}
