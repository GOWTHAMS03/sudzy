package com.sudzey.sudzey.dto;

import lombok.Data;

@Data
public class DeliveryStatusResponse {
    private String orderId;
    private String trackingNumber;
    private String status;
}
