package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.model.Wishlist;
import com.sudzey.sudzey.repository.WishlistRepository;
import com.sudzey.sudzey.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    @Override
    public Wishlist getWishlistByUserId(String userId) {
        return wishlistRepository.findByUserId(userId).orElseGet(() -> {
            Wishlist wishlist = new Wishlist();
            wishlist.setUserId(userId);
            return wishlistRepository.save(wishlist);
        });
    }

    @Override
    public Wishlist addProductToWishlist(String userId, String productId) {
        Wishlist wishlist = getWishlistByUserId(userId);
        if (!wishlist.getProductIds().contains(productId)) {
            wishlist.getProductIds().add(productId);
        }
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist removeProductFromWishlist(String userId, String productId) {
        Wishlist wishlist = getWishlistByUserId(userId);
        wishlist.getProductIds().remove(productId);
        return wishlistRepository.save(wishlist);
    }
}
