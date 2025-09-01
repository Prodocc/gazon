package com.eshop.productservice.mappers;

import com.eshop.productservice.dto.ProductCheckDto;
import com.eshop.productservice.dto.ProductResponseDto;
import com.eshop.productservice.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

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

        ProductResponseDto dto = productMapper.toResponseDto(p1);

        assertThat(dto).isNotNull();
        assertThat(dto.name()).isEqualTo(p1.getName());
        assertThat(dto.description()).isEqualTo(p1.getDescription());
        assertThat(dto.price().compareTo(p1.getPrice())).isEqualTo(0);
        assertThat(dto.quantity()).isEqualTo(p1.getQuantity());
    }

    @Test
    @DisplayName("nullProductToProductDtoMappingTest")
    public void shouldMapNullProductToProductDto() {
        Product p1 = null;

        ProductResponseDto dto = productMapper.toResponseDto(p1);

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