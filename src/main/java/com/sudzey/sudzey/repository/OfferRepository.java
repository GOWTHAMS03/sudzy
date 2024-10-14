package com.sudzey.sudzey.repository;

import com.sudzey.sudzey.model.Offer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OfferRepository extends MongoRepository<Offer, String> {
    List<Offer> findByApplicableProductIdsContainsAndStartTimeBeforeAndEndTimeAfter(
            String productId, LocalDateTime now1, LocalDateTime now2);
}
