package com.eshop.productservice.service;

import com.eshop.productservice.dto.ProductCreateRequest;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Product createProduct(ProductCreateRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setActive(true);

        return repo.save(product);
    }
}
