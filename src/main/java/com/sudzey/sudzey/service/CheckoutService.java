package com.sudzey.sudzey.service;

import com.sudzey.sudzey.dto.CheckoutDTO;
import com.sudzey.sudzey.dto.OrderDTO;

public interface CheckoutService {
    OrderDTO checkout(CheckoutDTO checkoutDTO);
}
