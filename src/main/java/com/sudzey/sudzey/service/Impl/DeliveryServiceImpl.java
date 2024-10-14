package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.dto.DeliveryRequest;
import com.sudzey.sudzey.dto.DeliveryStatusResponse;
import com.sudzey.sudzey.model.Order;
import com.sudzey.sudzey.model.OrderStatus;
import com.sudzey.sudzey.service.DeliveryService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DeliveryServiceImpl implements DeliveryService {
    private static final String DELIVERY_PARTNER_API_URL = "";
    private final RestTemplate restTemplate;


    public DeliveryServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public String createDelivery(Order order) {
        DeliveryRequest deliveryRequest = new DeliveryRequest(order);

        String response = restTemplate.postForObject(DELIVERY_PARTNER_API_URL + "/create", deliveryRequest, String.class);

        return response;
    }


    @Override
    public Order getDeliveryStatus(String trackingNumber) {
        DeliveryStatusResponse response = restTemplate.getForObject(DELIVERY_PARTNER_API_URL + "/track/" + trackingNumber, DeliveryStatusResponse.class);
        return mapToOrder(response);
    }

    private Order mapToOrder(DeliveryStatusResponse response) {
        Order order = new Order();
        order.setStatus(OrderStatus.valueOf(response.getStatus()));
        return order;
    }
}
