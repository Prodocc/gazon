package com.eshop.productservice.controller.integration;

import com.eshop.productservice.dto.ProductRequestDto;
import com.eshop.productservice.dto.ProductResponseDto;
import com.eshop.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIT {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @DynamicPropertySource
    static void setUp(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void cleanUp(){
        productRepository.deleteAll();
    }

    @Test
    void createProduct_shouldReturnProductAndReturn201() {
        ProductRequestDto requestDto = new ProductRequestDto("Test Laptop", "desc", BigDecimal.valueOf(100), 10);

        assertThat(productRepository.findAll()).isEmpty();

        String url = "/api/v1/products";

        ResponseEntity<ProductResponseDto> response = restTemplate.postForEntity(url, requestDto, ProductResponseDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Test Laptop");
    }
}
