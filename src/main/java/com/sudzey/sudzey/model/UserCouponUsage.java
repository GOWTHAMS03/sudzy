package com.sudzey.sudzey.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user_coupon_usage")
public class UserCouponUsage {
    @Id
    private String id;
    private String userId;
    private String couponId;
    private int usageCount;
}
