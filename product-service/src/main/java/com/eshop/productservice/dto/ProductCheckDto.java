package com.eshop.productservice.dto;

import java.math.BigDecimal;

public record ProductCheckDto(long id, BigDecimal price, boolean isActive) {
}
