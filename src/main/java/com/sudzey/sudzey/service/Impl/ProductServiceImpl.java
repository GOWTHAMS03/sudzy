package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.dto.ProductDTO;
import com.sudzey.sudzey.model.Category;
import com.sudzey.sudzey.model.Product;
import com.sudzey.sudzey.repository.ProductRepository;
import com.sudzey.sudzey.service.CategoryService;
import com.sudzey.sudzey.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, MongoTemplate mongoTemplate, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
        this.categoryService = categoryService;
    }

    @Override
    public Product createProduct(ProductDTO productDTO, String categoryId) {
        Category category = categoryService.getCategoryById(categoryId);

        if (category != null) {
            Product product = new Product();
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setImages(productDTO.getImages());
            product.setCategoryId(categoryId);
            return productRepository.save(product);
        }
        return null;
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id){
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(String id,ProductDTO productDTO){
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setImages(productDTO.getImages());
            return productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public  void deleteProduct(String id){
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProducts(String query){
        Query searchQuery = new Query();
        searchQuery.addCriteria(Criteria.where("name").regex(query,"i"));
        return mongoTemplate.find(searchQuery,Product.class);
    }

    @Override
    public Page<Product> getAllProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
