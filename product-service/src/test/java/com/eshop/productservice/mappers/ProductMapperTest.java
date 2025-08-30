package com.eshop.productservice.mappers;

import com.eshop.productservice.dto.ProductCheckDto;
import com.eshop.productservice.dto.ProductDto;
import com.eshop.productservice.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private ProductMapper productMapper;

    @BeforeEach
    public void setup() {
        this.productMapper = new ProductMapper();
    }


    @Test
    @DisplayName("productToProductDtoMappingTest")
    public void shouldMapProductToProductDto() {
        Product p1 = new Product();
        p1.setName("my_product");
        p1.setDescription("my_description");
        p1.setPrice(BigDecimal.valueOf(100));
        p1.setQuantity(10);

        ProductDto dto = productMapper.toDto(p1);

        assertThat(dto).isNotNull();
        assertThat(dto.getName()).isEqualTo(p1.getName());
        assertThat(dto.getDescription()).isEqualTo(p1.getDescription());
        assertThat(dto.getPrice().compareTo(p1.getPrice())).isEqualTo(0);
        assertThat(dto.getQuantity()).isEqualTo(p1.getQuantity());
    }

    @Test
    @DisplayName("nullProductToProductDtoMappingTest")
    public void shouldMapNullProductToProductDto() {
        Product p1 = null;

        ProductDto dto = productMapper.toDto(p1);

        assertThat(dto).isNull();
    }

    @Test
    @DisplayName("productToProductCheckDtoMappingTest")
    public void shouldMapProductToProductCheckDto() {
        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(BigDecimal.valueOf(100));
        p1.setActive(true);

        ProductCheckDto checkDto = productMapper.toCheckDto(p1);

        assertThat(checkDto).isNotNull();
        assertThat(checkDto).usingRecursiveComparison().isEqualTo(p1);
    }

    @Test
    @DisplayName("nullProductToProductCheckDtoMappingTest")
    public void shouldMapNullProductToProductCheckDto() {
        Product p1 = null;

        ProductCheckDto checkDto = productMapper.toCheckDto(p1);

        assertThat(checkDto).isNull();
    }
}