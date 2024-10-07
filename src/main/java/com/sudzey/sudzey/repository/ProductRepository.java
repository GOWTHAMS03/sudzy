package com.sudzey.sudzey.repository;

import com.sudzey.sudzey.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, Long> {
    // Custom query methods can be added here if needed
}
