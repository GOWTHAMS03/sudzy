package com.sudzey.sudzey.dto;

import com.sudzey.sudzey.model.Order;
import com.sudzey.sudzey.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequest {
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private String shippingAddress;
    public DeliveryRequest(Order order) {
        this.orderId = order.getId();
        this.userId = order.getUserId();

        if (!order.getOrderItems().isEmpty()) {
            OrderItem firstItem = order.getOrderItems().get(0);
            this.productId = firstItem.getProductId();
            this.quantity = firstItem.getQuantity();
        }

        this.shippingAddress = "User's shipping address";
    }

}
