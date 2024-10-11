package com.sudzey.sudzey.controller;

import com.sudzey.sudzey.model.Wishlist;
import com.sudzey.sudzey.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    // Get Wishlist for a particular user
    @GetMapping
    public ResponseEntity<Wishlist> getWishlist(Authentication authentication) {
        String userId = authentication.getName(); // Assuming JWT provides the user ID
        Wishlist wishlist = wishlistService.getWishlistByUserId(userId);
        return ResponseEntity.ok(wishlist);
    }

    // Add a product to wishlist
    @PostMapping("/add")
    public ResponseEntity<Wishlist> addProductToWishlist(@RequestParam String productId, Authentication authentication) {
        String userId = authentication.getName(); // Assuming JWT provides the user ID
        Wishlist wishlist = wishlistService.addProductToWishlist(userId, productId);
        return ResponseEntity.ok(wishlist);
    }

    // Remove a product from wishlist
    @DeleteMapping("/remove")
    public ResponseEntity<Wishlist> removeProductFromWishlist(@RequestParam String productId, Authentication authentication) {
        String userId = authentication.getName(); // Assuming JWT provides the user ID
        Wishlist wishlist = wishlistService.removeProductFromWishlist(userId, productId);
        return ResponseEntity.ok(wishlist);
    }
}
