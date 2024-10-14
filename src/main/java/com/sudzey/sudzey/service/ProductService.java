package com.sudzey.sudzey.service;

import com.sudzey.sudzey.dto.ProductDTO;
import com.sudzey.sudzey.model.Product;
import com.sudzey.sudzey.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO);
    List<Product> getAllProducts();
    Product getProductById(String id);
    Product updateProduct(String id, ProductDTO productDTO);
    void deleteProduct(String id);
    List<Product> searchProducts(String query);
    Page<Product> getAllProductsPaginated(Pageable pageable);
    List<ProductDTO> getTrendingProducts();
    List<ProductDTO> getBestsellerProducts();
    ProductDTO convertToDTO(Product product);
    void updateProductSalesCount(Product product);
    void updateProductViews(Product product);
    void updateProductStatus(Product product);
    void addReview(String userId, String productId, String content, int rating);
    List<Review> getReviewsForProduct(String productId);

}