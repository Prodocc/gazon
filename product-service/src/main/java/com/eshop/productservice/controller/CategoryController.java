package com.eshop.productservice.controller;

import com.eshop.productservice.dto.CategoryDto;
import com.eshop.productservice.dto.ProductDto;
import com.eshop.productservice.mappers.ProductMapper;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public CategoryController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Page<ProductDto>> getProductByCategory(@PathVariable long categoryId,
                                                                 @RequestParam int page,
                                                                 @RequestParam int size) {
        Page<Product> productsByCategory = productService.findProductsByCategory(categoryId, page, size);

        return ResponseEntity.ok(productsByCategory.map(productMapper::toDto));
    }

}
