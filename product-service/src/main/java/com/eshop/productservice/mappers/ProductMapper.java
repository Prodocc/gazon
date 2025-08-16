package com.eshop.productservice.mappers;

import com.eshop.productservice.dto.ProductDto;
import com.eshop.productservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product){
        if (product == null){
            return null;
        }

        ProductDto dto = new ProductDto();
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setQuantity(product.getQuantity());

        return dto;
    }
}
