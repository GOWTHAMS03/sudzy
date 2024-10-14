package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.model.Offer;
import com.sudzey.sudzey.repository.OfferRepository;
import com.sudzey.sudzey.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class OfferServiceImpl implements OfferService {
    @Autowired
    private OfferRepository offerRepository;
    @Override
    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }
     @Override
    public List<Offer> getActiveOffersForProduct(String productId) {
        LocalDateTime now = LocalDateTime.now();
        return offerRepository.findByApplicableProductIdsContainsAndStartTimeBeforeAndEndTimeAfter(productId, now, now);
    }
}
