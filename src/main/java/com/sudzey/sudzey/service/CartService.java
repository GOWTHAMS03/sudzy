package com.sudzey.sudzey.service;

import com.sudzey.sudzey.model.Cart;

public interface CartService {
    Cart getCartByUserId(String userId);
    Cart addProductToCart(String userId, String productId, int quantity);
    Cart updateProductQuantityInCart(String userId, String productId, int quantity);
    Cart removeProductFromCart(String userId, String productId);
    void clearCart(String userId);
}
