package com.sudzey.sudzey.dto;

import com.sudzey.sudzey.enumerate.PaymentMethod;
import com.sudzey.sudzey.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private User userId;
    private List<OrderItemDTO> orderItems;
    private double totalPrice;
    private PaymentMethod paymentMethod;
    private double finalAmount;
    private String couponCode;
}