package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.exceptions.CouponNotFoundException;
import com.sudzey.sudzey.exceptions.InvalidCouponException;
import com.sudzey.sudzey.model.Coupon;
import com.sudzey.sudzey.repository.CouponRepository;
import com.sudzey.sudzey.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {


    @Autowired
    private CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public Coupon validateAndApplyCoupon(String code, double totalAmount, String userId) throws InvalidCouponException {
        Optional<Coupon> couponOpt = couponRepository.findByCode(code);
        if (!couponOpt.isPresent()) {
            throw new CouponNotFoundException("Coupon code not found");
        }

        Coupon coupon = couponOpt.get();
        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(coupon.getValidFrom()) || now.isAfter(coupon.getValidUntil())) {
            throw new InvalidCouponException("Coupon is expired or not yet active");
        }

        if (coupon.getCurrentUsage() >= coupon.getMaxUsage()) {
            throw new InvalidCouponException("Coupon has reached maximum usage");
        }

        int userCouponUsage = getUserCouponUsage(userId, coupon); // Implement this logic
        if (userCouponUsage >= coupon.getMaxUsagePerUser()) {
            throw new InvalidCouponException("You have already used this coupon the maximum number of times");
        }

        // Calculate the discount
        double discount = 0;
        if (coupon.isPercentage()) {
            discount = totalAmount * (coupon.getDiscountAmount() / 100);
        } else {
            discount = coupon.getDiscountAmount();
        }

        // Apply the discount
        double finalAmount = totalAmount - discount;

        // Increment the coupon's usage count
        coupon.setCurrentUsage(coupon.getCurrentUsage() + 1);
        couponRepository.save(coupon);

        return coupon; // Return the coupon for record-keeping (you can modify this)
    }


    @Override
    public int getUserCouponUsage(String userId, Coupon coupon) {
        return 0;
    }
}