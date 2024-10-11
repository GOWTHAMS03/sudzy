package com.sudzey.sudzey.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String userId;
    private List<OrderItemDTO> orderItems;
    private double totalPrice;
}