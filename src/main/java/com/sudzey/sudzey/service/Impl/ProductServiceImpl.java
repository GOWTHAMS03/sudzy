package com.sudzey.sudzey.service.Impl;

import com.sudzey.sudzey.dto.ProductDTO;
import com.sudzey.sudzey.exceptions.ProductNotFoundException;
import com.sudzey.sudzey.model.*;
import com.sudzey.sudzey.repository.OfferRepository;
import com.sudzey.sudzey.repository.ProductRepository;
import com.sudzey.sudzey.repository.ReviewRepository;
import com.sudzey.sudzey.repository.UserRepository;
import com.sudzey.sudzey.service.CategoryService;
import com.sudzey.sudzey.service.EmailService;
import com.sudzey.sudzey.service.ProductService;
import com.sudzey.sudzey.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    OfferRepository offerRepository;
    @Autowired
    private MongoTemplate mongoTemplate;


    private final CategoryService categoryService;

    private final UserService userService;
    private final EmailService emailService;

    public ProductServiceImpl(ProductRepository productRepository, MongoTemplate mongoTemplate, CategoryService categoryService, UserService userService, EmailService emailService) {
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
        this.categoryService = categoryService;
        this.userService = userService;
        this.emailService = emailService;
    }

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());

        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.isStock());
        product.setQuantity(productDTO.getQuantity());
        product.setImages(productDTO.getImages());
        Category category = categoryService.getCategoryById(productDTO.getCategory().getId());

        if (category == null) {
            throw new RuntimeException("Category not found");
        }
        product.setCategory(category);
        return productRepository.save(product);
    }

    private void sendNewsletter(Product product) {
        String subject = "New Product Added: " + product.getName();
        String body = "Check out our new product: " + product.getName() + "\n" + product.getName() + "\nPrice: $" + product.getPrice();

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            emailService.sendEmail(user.getEmail(), subject, body);
        }
    }


    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product updateProduct(String id, ProductDTO productDTO) {
        Product existingProduct = getProductById(id);
        if (existingProduct != null) {
            existingProduct.setName(productDTO.getName());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setImages(productDTO.getImages());
            existingProduct.setCategory(productDTO.getCategory());

            return productRepository.save(existingProduct);
        }
        throw new ProductNotFoundException("Product with ID " + id + " not found.");
    }

    @Override
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> searchProducts(String query) {
        Query searchQuery = new Query();
        searchQuery.addCriteria(Criteria.where("name").regex(query, "i"));
        return mongoTemplate.find(searchQuery, Product.class);
    }

    @Override
    public Page<Product> getAllProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<ProductDTO> getTrendingProducts() {
        List<Product> trendingProducts = productRepository.findByIsTrendingTrue();
        return trendingProducts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getBestsellerProducts() {
        List<Product> bestsellerProducts = productRepository.findByIsBestsellerTrue();
        return bestsellerProducts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void updateProductViews(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProductSalesCount(Product product) {
        productRepository.save(product);
    }


    @Override
    public void updateProductStatus(Product product) {
        // Mark product as trending based on views
        if (product.getViews() > 1000) {
            product.setTrending(true);
        } else {
            product.setTrending(false);
        }

        // Mark product as bestseller based on salesCount
        if (product.getSalesCount() > 500) {
            product.setBestseller(true);
        } else {
            product.setBestseller(false);
        }

        // Save the updated product
        productRepository.save(product);
    }

    @Override
    public void addReview(String userId, String productId, String content, int rating) {
        // Check if user has purchased the product
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPurchasedProductIds().contains(productId)) {
            throw new RuntimeException("User has not purchased this product");
        }

        // Create and save the review
        Review review = new Review();
        review.setUserId(userId);
        review.setProductId(productId);
        review.setContent(content);
        review.setRating(rating);
        review.setCreatedAt(java.time.LocalDateTime.now().toString());

        reviewRepository.save(review);
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.getReviewIds().add(review.getId());
        productRepository.save(product);
    }

    @Override
    public List<Review> getReviewsForProduct(String productId) {
        return reviewRepository.findByProductId(productId);
    }





    public ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setPrice(calculatePriceWithOffer(product));
        dto.setStock(product.isStock());
        dto.setQuantity(product.getQuantity());
        dto.setImages(product.getImages());
        dto.setSalesCount(product.getSalesCount());
        dto.setViews(product.getViews());
        dto.setTrending(product.isTrending());
        dto.setBestseller(product.isBestseller());
        dto.setCategory(product.getCategory());
        return dto;
    }

    private double calculatePriceWithOffer(Product product) {
        LocalDateTime now = LocalDateTime.now();
        List<Offer> currentOffers = offerRepository.findByApplicableProductIdsContainsAndStartTimeBeforeAndEndTimeAfter(
                product.getId(), now, now);

        if (!currentOffers.isEmpty()) {
            Optional<Offer> bestOffer = currentOffers.stream()
                    .max((o1, o2) -> Double.compare(o1.getDiscountPercentage(), o2.getDiscountPercentage()));
            if (bestOffer.isPresent()) {
                double discount = bestOffer.get().getDiscountPercentage();
                return product.getPrice() * ((100 - discount) / 100);
            }
        }
        return product.getPrice();
    }
}
