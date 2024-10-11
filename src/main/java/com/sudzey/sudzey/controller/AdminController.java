package com.sudzey.sudzey.controller;

import com.sudzey.sudzey.dto.ProductDTO;
import com.sudzey.sudzey.model.Category;
import com.sudzey.sudzey.model.Order;
import com.sudzey.sudzey.model.Product;
import com.sudzey.sudzey.service.CategoryService;
import com.sudzey.sudzey.service.OrderService;
import com.sudzey.sudzey.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductService productService;
    private final OrderService orderService;
    private final CategoryService categoryService;


    @GetMapping()
    public ResponseEntity<String> sayHi() {
        return ResponseEntity.ok("HI ADMIN");
    }


//    product control
        @PostMapping("/admin/create")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO, @RequestParam String categoryId) {
            productService.createProduct(productDTO, categoryId);
            return ResponseEntity.ok("Product created successfully!");
        }



    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        Product updatedProduct = productService.updateProduct(id, productDTO);
        return updatedProduct != null ? ResponseEntity.ok("Product updated successfully!") : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }

//    order control

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getallorders")
    public ResponseEntity<Page<Order>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Order> orders = orderService.getAllOrders(PageRequest.of(page, size));
        return ResponseEntity.ok(orders);
    }

}
