package com.eshop.productservice.mappers;

import com.eshop.productservice.dto.ProductCheckDto;
import com.eshop.productservice.dto.ProductDto;
import com.eshop.productservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product){
        if (product == null){
            return null;
        }

        return new ProductDto(product.getName(), product.getDescription(), product.getPrice(), product.getQuantity());
    }

    public ProductCheckDto toCheckDto(Product product){
        if (product == null){
            return null;
        }

        return new ProductCheckDto(product.getId(), product.getPrice(), product.isActive());
    }
}
