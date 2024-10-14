package com.sudzey.sudzey.service;

import com.sudzey.sudzey.model.Offer;
import com.sudzey.sudzey.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface OfferService {

    Offer createOffer(Offer offer);
    List<Offer> getActiveOffersForProduct(String productId);
}
