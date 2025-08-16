package com.eshop.productservice.controller;

import com.eshop.productservice.dto.ProductDto;
import com.eshop.productservice.mappers.ProductMapper;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService service, ProductMapper productMapper) {
        this.productService = service;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto request) {
        Product created = productService.createProduct(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getProductsPaginated(
            @RequestParam int page,
            @RequestParam int size) {

        Page<Product> productPage = productService.findProductsPaginated(page, size);

        return ResponseEntity.ok(productPage.map(productMapper::toDto));

    }

}
