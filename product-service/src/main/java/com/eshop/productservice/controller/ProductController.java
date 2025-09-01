package com.eshop.productservice.controller;

import com.eshop.productservice.dto.ProductRequestDto;
import com.eshop.productservice.dto.ProductResponseDto;
import com.eshop.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService service) {
        this.productService = service;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto request) {
        ProductResponseDto created = productService.createProduct(request);

        URI location = URI.create(String.format("api/v1/products/%d", created.id()));
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getProductsPaginated(
            @RequestParam int page,
            @RequestParam int size) {

        Page<ProductResponseDto> productsPaginated = productService.findProductsPaginated(page, size);

        return ResponseEntity.ok(productsPaginated);

    }

}
