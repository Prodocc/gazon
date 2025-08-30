package com.eshop.productservice.service;

import com.eshop.productservice.dto.ProductCheckDto;
import com.eshop.productservice.dto.ProductDto;
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

    public Product createProduct(ProductDto request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setActive(true);
        return productRepository.save(product);
    }

    public Page<ProductDto> findProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(pageable).map(productMapper::toDto);
    }

    public Page<ProductDto> findProductsByCategory(long category_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAllByCategory(category_id, pageable).map(productMapper::toDto);
    }

    public List<ProductCheckDto> checkProducts(List<Long> productsIds) {
        if (productsIds == null){
            throw new IllegalArgumentException("Product ID list cannot be null");
        }

        if (productsIds.isEmpty()){
            return Collections.emptyList();
        }
        return productRepository.findAllById(productsIds).stream().map(productMapper::toCheckDto).toList();
    }

    public Optional<ProductDto> findProductById(long id) {
        Optional<Product> byId = productRepository.findById(id);

        return byId.map(productMapper::toDto);
    }
}
