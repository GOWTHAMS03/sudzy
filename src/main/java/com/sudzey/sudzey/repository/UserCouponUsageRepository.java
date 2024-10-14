package com.sudzey.sudzey.repository;

import com.sudzey.sudzey.model.UserCouponUsage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserCouponUsageRepository extends MongoRepository<UserCouponUsage, String> {
    Optional<UserCouponUsage> findByUserIdAndCouponId(String userId, String couponId);
}
