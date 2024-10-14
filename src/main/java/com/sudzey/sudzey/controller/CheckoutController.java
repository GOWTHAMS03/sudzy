package com.sudzey.sudzey.controller;

import com.sudzey.sudzey.dto.CheckoutDTO;
import com.sudzey.sudzey.dto.OrderDTO;
import com.sudzey.sudzey.service.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PostMapping("/process")
    public ResponseEntity<OrderDTO> processCheckout(@RequestBody CheckoutDTO checkoutDTO) {
        try {
            OrderDTO orderDTO = checkoutService.checkout(checkoutDTO);
            return ResponseEntity.ok(orderDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
