package com.sudzey.sudzey.controller;

import com.sudzey.sudzey.model.Offer;
import com.sudzey.sudzey.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @PostMapping("/create")
    public ResponseEntity<Offer> createOffer(@RequestBody Offer offer) {
        Offer savedOffer = offerService.createOffer(offer);
        return ResponseEntity.ok(savedOffer);
    }

    @GetMapping("/active/{productId}")
    public ResponseEntity<List<Offer>> getActiveOffers(@PathVariable String productId) {
        List<Offer> activeOffers = offerService.getActiveOffersForProduct(productId);
        return ResponseEntity.ok(activeOffers);
    }
}
