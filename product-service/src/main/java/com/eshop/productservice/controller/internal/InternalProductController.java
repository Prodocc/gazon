package com.eshop.productservice.controller.internal;

import com.eshop.productservice.dto.ProductCheckDto;
import com.eshop.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/internal/products")
public class InternalProductController {

    private final ProductService productService;

    public InternalProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/check")
    public ResponseEntity<List<ProductCheckDto>> checkProductStatus(
            @RequestParam("ids") List<Long> productIds) {
        return ResponseEntity.ok(productService.checkProducts(productIds));
    }
}
