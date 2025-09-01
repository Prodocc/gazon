package com.eshop.productservice.service;

import com.eshop.productservice.dto.ProductCheckDto;
import com.eshop.productservice.dto.ProductRequestDto;
import com.eshop.productservice.dto.ProductResponseDto;
import com.eshop.productservice.mappers.ProductMapper;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponseDto createProduct(ProductRequestDto request) {
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setQuantity(request.quantity());
        product.setActive(true);
        productRepository.save(product);
        return productMapper.toResponseDto(product);
    }

    public Page<ProductResponseDto> findProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(pageable).map(productMapper::toResponseDto);
    }

    public Page<ProductResponseDto> findProductsByCategory(long category_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAllByCategory(category_id, pageable).map(productMapper::toResponseDto);
    }

    public List<ProductCheckDto> checkProducts(List<Long> productsIds) {
        if (productsIds == null) {
            throw new IllegalArgumentException("Product ID list cannot be null");
        }

        if (productsIds.isEmpty()) {
            return Collections.emptyList();
        }
        return productRepository.findAllById(productsIds).stream().map(productMapper::toCheckDto).toList();
    }

    public Optional<ProductResponseDto> findProductById(long id) {
        Optional<Product> byId = productRepository.findById(id);

        return byId.map(productMapper::toResponseDto);
    }
}
