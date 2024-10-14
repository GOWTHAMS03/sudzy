package com.sudzey.sudzey.controller;

import com.sudzey.sudzey.dto.ProductDTO;
import com.sudzey.sudzey.model.Product;
import com.sudzey.sudzey.model.Review;
import com.sudzey.sudzey.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getallproduct")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            // Increment product views
            product.setViews(product.getViews() + 1);
            productService.updateProductViews(product);

            // Update trending status based on views
            productService.updateProductStatus(product);
        }

        ProductDTO productDTO = productService.convertToDTO(product);
        return ResponseEntity.ok(productDTO);
           }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        List<Product> products = productService.searchProducts(query);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Product>> getAllProductsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Product> products = productService.getAllProductsPaginated(PageRequest.of(page, size));
        return ResponseEntity.ok(products);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<ProductDTO>> getTrendingProducts() {
        List<ProductDTO> trendingProducts = productService.getTrendingProducts();
        return ResponseEntity.ok(trendingProducts);
    }

    @GetMapping("/bestsellers")
    public ResponseEntity<List<ProductDTO>> getBestsellerProducts() {
        List<ProductDTO> bestsellerProducts = productService.getBestsellerProducts();
        return ResponseEntity.ok(bestsellerProducts);
    }

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<String> addReview(
            @RequestParam String userId,
            @PathVariable String productId,
            @RequestParam String content,
            @RequestParam int rating) {
        productService.addReview(userId, productId, content, rating);
        return ResponseEntity.ok("Review added successfully");
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsForProduct(@PathVariable String productId) {
        List<Review> reviews = productService.getReviewsForProduct(productId);
        return ResponseEntity.ok(reviews);
    }
}
