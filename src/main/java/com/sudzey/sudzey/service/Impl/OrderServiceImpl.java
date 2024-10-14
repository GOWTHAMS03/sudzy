package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.dto.OrderDTO;
import com.sudzey.sudzey.dto.OrderItemDTO;
import com.sudzey.sudzey.model.Order;
import com.sudzey.sudzey.model.OrderItem;
import com.sudzey.sudzey.model.OrderStatus;
import com.sudzey.sudzey.model.Product;
import com.sudzey.sudzey.repository.OrderRepository;
import com.sudzey.sudzey.repository.ProductRepository;
import com.sudzey.sudzey.service.DeliveryService;
import com.sudzey.sudzey.service.OrderService;
import com.sudzey.sudzey.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final DeliveryService deliveryService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, ProductService productService, DeliveryService deliveryService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productService = productService;
        this.deliveryService = deliveryService;
    }

    @Override
    public Order placeOrder(OrderDTO orderDTO) {
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
            Product product = productRepository.findById(itemDTO.getProductId()).orElse(null);
            if (product == null || !product.isStock() || product.getQuantity() < itemDTO.getQuantity()) {
                throw new IllegalStateException("Product not available or insufficient stock");
            }

            product.setQuantity(product.getQuantity() - itemDTO.getQuantity());
            if(product.getQuantity() <=0){
                product.setStock(false);
            }
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(itemDTO.getProductId());
            orderItem.setQuantity(itemDTO.getQuantity());
            orderItem.setPrice(product.getPrice() * itemDTO.getQuantity());

            product.setSalesCount(product.getSalesCount() + itemDTO.getQuantity());
            productService.updateProductSalesCount(product);

            productService.updateProductViews(product);

            productService.updateProductStatus(product);

            orderItems.add(orderItem);

        }

        Order order = new Order();
        order.setUserId(orderDTO.getUserId().getId());
        order.setOrderItems(orderItems);
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        order.setPaymentMethod(orderDTO.getPaymentMethod());

//        String trackingNumber = deliveryService.createDelivery(order);
//        order.setTrackingNumber(trackingNumber);

        return orderRepository.save(order);

    }

    @Override
    public Order updateOrderStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
            return orderRepository.save(order);
        }
        return null;
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
}
