package com.eshop.productservice.service;

import com.eshop.productservice.dto.ProductDto;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public Product createProduct(ProductDto request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setActive(true);
        return repo.save(product);
    }

    public Page<Product> findProductsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repo.findAll(pageable);
    }

    public Page<Product> findProductsByCategory(long category_id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repo.findAllByCategory(category_id, pageable);
    }
}
