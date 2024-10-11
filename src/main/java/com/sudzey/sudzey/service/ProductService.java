package com.sudzey.sudzey.service;

import com.sudzey.sudzey.dto.ProductDTO;
import com.sudzey.sudzey.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductDTO productDTO, String categoryId);
    List<Product> getAllProducts();
    Product getProductById(String id);
    Product updateProduct(String id, ProductDTO productDTO);
    void deleteProduct(String id);
    List<Product> searchProducts(String query);
    Page<Product> getAllProductsPaginated(Pageable pageable);
}