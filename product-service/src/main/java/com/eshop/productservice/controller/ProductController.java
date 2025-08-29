package com.eshop.productservice.controller;

import com.eshop.productservice.dto.ProductCreateRequest;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductCreateRequest request) {
        Product created = service.createProduct(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        Product product = service.getProductById(id);
        return ResponseEntity.ok(product);
    }



}
