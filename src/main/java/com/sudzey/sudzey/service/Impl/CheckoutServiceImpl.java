package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.dto.CheckoutDTO;
import com.sudzey.sudzey.dto.OrderDTO;
import com.sudzey.sudzey.exceptions.InvalidCouponException;
import com.sudzey.sudzey.model.Coupon;
import com.sudzey.sudzey.service.CheckoutService;
import com.sudzey.sudzey.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    @Autowired
    private CouponService couponService;

    @Override
    public OrderDTO checkout(CheckoutDTO checkoutDTO) throws InvalidCouponException {
        double totalAmount = checkoutDTO.getTotalAmount();
        String couponCode = checkoutDTO.getCouponCode();
        String userId = checkoutDTO.getUserId();

        if (couponCode != null && !couponCode.isEmpty()) {
            Coupon coupon = couponService.validateAndApplyCoupon(couponCode, totalAmount, userId);
            totalAmount -= coupon.getDiscountAmount();
        }

        // Proceed with creating the order (not shown here)
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setFinalAmount(totalAmount);
        orderDTO.setCouponCode(couponCode);

        // Save the order in your order repository (not shown)

        return orderDTO;
    }
}
