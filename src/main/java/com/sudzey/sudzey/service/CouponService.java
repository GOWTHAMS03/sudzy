package com.sudzey.sudzey.service;


import com.sudzey.sudzey.model.Coupon;

public interface CouponService {
    Coupon createCoupon(Coupon coupon);
    Coupon validateAndApplyCoupon(String code, double totalAmount, String userId);
    int getUserCouponUsage(String userId, Coupon coupon);
}
