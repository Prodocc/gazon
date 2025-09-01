package com.eshop.productservice.controller;

import com.eshop.productservice.dto.CategoryDto;
import com.eshop.productservice.dto.ProductResponseDto;
import com.eshop.productservice.service.CategoryService;
import com.eshop.productservice.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public CategoryController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Page<ProductResponseDto>> getProductByCategory(@PathVariable long categoryId,
                                                                         @RequestParam int page,
                                                                         @RequestParam int size) {
        Page<ProductResponseDto> productsByCategory = productService.findProductsByCategory(categoryId, page, size);

        return ResponseEntity.ok(productsByCategory);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

}
