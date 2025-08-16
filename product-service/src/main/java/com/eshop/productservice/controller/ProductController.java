package com.eshop.productservice.controller;

import com.eshop.productservice.dto.ProductDto;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService service) {
        this.productService = service;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto request) {
        Product created = productService.createProduct(request);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public Page<ProductDto> getProductsPaginated(
            @RequestParam int page,
            @RequestParam int size) {

        Page<Product> productPage = productService.findProductsPaginated(page, size);

        return productPage.map(this::convertToDto);

    }

    private ProductDto convertToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        return dto;
    }

}
