package com.sudzey.sudzey.repository;

import com.sudzey.sudzey.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByProductId(String productId);
    List<Review> findByUserIdAndProductId(String userId, String productId);
}
