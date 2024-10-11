package com.sudzey.sudzey.service;

import com.sudzey.sudzey.model.Wishlist;

public interface WishlistService {
    Wishlist getWishlistByUserId(String userId);
    Wishlist addProductToWishlist(String userId, String productId);
    Wishlist removeProductFromWishlist(String userId, String productId);
}
