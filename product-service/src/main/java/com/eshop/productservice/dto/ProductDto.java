package com.eshop.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductDto {
    private final String name;
    private final String description;
    private final BigDecimal price;
    private final int quantity;
}
