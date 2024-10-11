package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.model.Cart;
import com.sudzey.sudzey.repository.CartRepository;
import com.sudzey.sudzey.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public Cart getCartByUserId(String userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUserId(userId);
            return cartRepository.save(cart);
        });
    }

    @Override
    public Cart addProductToCart(String userId, String productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        cart.getProducts().put(productId, cart.getProducts().getOrDefault(productId, 0) + quantity);
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateProductQuantityInCart(String userId, String productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        if (quantity <= 0) {
            cart.getProducts().remove(productId);
        } else {
            cart.getProducts().put(productId, quantity);
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(String userId, String productId) {
        Cart cart = getCartByUserId(userId);
        cart.getProducts().remove(productId);
        return cartRepository.save(cart);
    }

    @Override
    public void clearCart(String userId) {
        Cart cart = getCartByUserId(userId);
        cart.getProducts().clear();
        cartRepository.save(cart);
    }
}
