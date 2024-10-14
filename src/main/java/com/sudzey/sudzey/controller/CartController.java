package com.sudzey.sudzey.controller;

import com.sudzey.sudzey.model.Cart;
import com.sudzey.sudzey.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    @GetMapping
    public ResponseEntity<Cart> getCart(Authentication authentication) {
        String userId = authentication.getName();
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }
    @PostMapping("/add")
    public ResponseEntity<Cart> addProductToCart(@RequestParam String productId, @RequestParam int quantity, Authentication authentication) {
        String userId = authentication.getName();
        Cart cart = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }
    @PutMapping("/update")
    public ResponseEntity<Cart> updateProductQuantityInCart(@RequestParam String productId, @RequestParam int quantity, Authentication authentication) {
        String userId = authentication.getName();
        Cart cart = cartService.updateProductQuantityInCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }
    @DeleteMapping("/remove")
    public ResponseEntity<Cart> removeProductFromCart(@RequestParam String productId, Authentication authentication) {
        String userId = authentication.getName();
        Cart cart = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }
    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart(Authentication authentication) {
        String userId = authentication.getName();
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully!");
    }
}
