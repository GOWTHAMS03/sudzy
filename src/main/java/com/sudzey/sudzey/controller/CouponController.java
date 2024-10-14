package com.sudzey.sudzey.controller;

import com.sudzey.sudzey.model.Coupon;
import com.sudzey.sudzey.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @PostMapping("/create")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.ok(createdCoupon);
    }

    @GetMapping("/validate")
    public ResponseEntity<Coupon> validateCoupon(@RequestParam String code, @RequestParam double totalAmount, @RequestParam String userId) {
        try {
            Coupon coupon = couponService.validateAndApplyCoupon(code, totalAmount, userId);
            return ResponseEntity.ok(coupon);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
