package com.eshop.productservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductCheckDto {
    private final long id;
    private final BigDecimal price;
    private final boolean isActive;
}
