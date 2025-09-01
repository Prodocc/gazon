package com.eshop.productservice.service;

import com.eshop.productservice.dto.ProductCheckDto;
import com.eshop.productservice.dto.ProductRequestDto;
import com.eshop.productservice.dto.ProductResponseDto;
import com.eshop.productservice.mappers.ProductMapper;
import com.eshop.productservice.model.Product;
import com.eshop.productservice.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("checkProducts should return correct DTO list for existing IDs")
    public void checkProductsShouldReturnProductCheckDtoListForExistingIds() {
        List<Long> ids = List.of(1L, 2L);

        Product p1 = new Product();
        p1.setId(1L);
        p1.setPrice(BigDecimal.valueOf(100));
        p1.setActive(true);

        Product p2 = new Product();
        p2.setId(2L);
        p2.setPrice(BigDecimal.valueOf(200));
        p2.setActive(false);

        List<Product> foundProducts = List.of(p1, p2);

        ProductCheckDto dto1 = new ProductCheckDto(1L, BigDecimal.valueOf(100), true);
        ProductCheckDto dto2 = new ProductCheckDto(2L, BigDecimal.valueOf(200), false);

        when(productRepository.findAllById(ids)).thenReturn(foundProducts);

        when(productMapper.toCheckDto(p1)).thenReturn(dto1);
        when(productMapper.toCheckDto(p2)).thenReturn(dto2);

        List<ProductCheckDto> result = productService.checkProducts(ids);

        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);

        assertThat(result).containsExactlyInAnyOrder(dto1, dto2);

        verify(productRepository, times(1)).findAllById(ids);
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 10",
            "0, 0",
            "0, -1"
    })
    @DisplayName("findAllPaginated should throw IllegalArgumentException for incorrect paging")
    public void findAllProductPaginatedShouldThrowException_whenPagingIsNotCorrect(int page, int size) {

        assertThrows(IllegalArgumentException.class,
                () -> productService.findProductsPaginated(page, size));

        verifyNoInteractions(productRepository);
        verifyNoInteractions(productMapper);
    }

    @Test
    @DisplayName("findAllPaginated should return productDtoPage with correct content and metadata")
    public void findAllProductsPaginatedShouldReturnProductDtoPageWithCorrectContentAndMetaData() {
        int page = 0;
        int size = 2;

        Product p1 = new Product();
        p1.setName("p1");
        p1.setDescription("p1 desc");
        p1.setPrice(BigDecimal.valueOf(100));
        p1.setQuantity(10);

        Product p2 = new Product();
        p2.setName("p2");
        p2.setDescription("p2 desc");
        p2.setPrice(BigDecimal.valueOf(200));
        p2.setQuantity(20);

        ProductResponseDto dto1 = new ProductResponseDto(p1.getId(), p1.getName(), p1.getDescription(), p1.getPrice(), p1.getQuantity());
        ProductResponseDto dto2 = new ProductResponseDto(p2.getId(), p2.getName(), p2.getDescription(), p2.getPrice(), p2.getQuantity());

        Page<Product> productPage = new PageImpl<>(List.of(p1, p2));

        when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);

        when(productMapper.toResponseDto(p1)).thenReturn(dto1);
        when(productMapper.toResponseDto(p2)).thenReturn(dto2);

        Page<ProductResponseDto> resultPage = productService.findProductsPaginated(page, size);

        assertThat(resultPage).isNotNull();
        assertThat(resultPage.getTotalElements()).isEqualTo(2);
        assertThat(resultPage.getContent()).hasSize(2);
        assertThat(resultPage.getContent()).containsExactly(dto1, dto2);

        ArgumentCaptor<Pageable> pac = ArgumentCaptor.forClass(Pageable.class);
        verify(productRepository, times(1)).findAll(pac.capture());

        Pageable value = pac.getValue();
        assertThat(value.getPageNumber()).isEqualTo(page);
        assertThat(value.getPageSize()).isEqualTo(size);
    }

    @Test
    @DisplayName("findProduct by id should return correct Optional<ProductDto>")
    public void findProductByIdShouldReturnCorrectProductDto() {
        Long id = 1L;

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("p1");
        p1.setDescription("p1 desc");
        p1.setPrice(BigDecimal.valueOf(100));
        p1.setQuantity(10);

        ProductResponseDto dto1 = new ProductResponseDto(p1.getId(), p1.getName(), p1.getDescription(), p1.getPrice(), p1.getQuantity());

        when(productRepository.findById(id)).thenReturn(Optional.of(p1));
        when(productMapper.toResponseDto(p1)).thenReturn(dto1);

        Optional<ProductResponseDto> result = productService.findProductById(id);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(dto1);

        verify(productRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("findProduct by id should return Optional.empty() when product with current id doesn't exists")
    public void findProductByIdShouldReturnOptionalEmpty_whenProductDoesNotExists() {
        Long id = 1L;

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Optional<ProductResponseDto> result = productService.findProductById(id);

        assertThat(result).isEmpty();

        verify(productRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("checkProducts should return only that a exists in database")
    public void checkProductsShouldReturnCorrectAmountOfProducts() {
        List<Long> list = List.of(1L, 2L, 3L);

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("p1");
        p1.setDescription("p1 desc");
        p1.setPrice(BigDecimal.valueOf(100));
        p1.setQuantity(10);

        Product p3 = new Product();
        p3.setId(3L);
        p3.setName("p3");
        p3.setDescription("p3 desc");
        p3.setPrice(BigDecimal.valueOf(300));
        p3.setQuantity(30);

        ProductCheckDto pcd1 = new ProductCheckDto(p1.getId(), p1.getPrice(), p1.isActive());
        ProductCheckDto pcd3 = new ProductCheckDto(p3.getId(), p3.getPrice(), p3.isActive());


        when(productRepository.findAllById(list)).thenReturn(List.of(p1, p3));
        when(productMapper.toCheckDto(p1)).thenReturn(pcd1);
        when(productMapper.toCheckDto(p3)).thenReturn(pcd3);

        List<ProductCheckDto> result = productService.checkProducts(list);

        assertThat(result).isNotNull();
        assertThat(result).containsExactlyInAnyOrder(pcd1, pcd3);
    }

    @Test
    @DisplayName("checkProducts should throw Exception when input list is null")
    public void checkProductsShouldThrowException_whenInputListIsNull() {
        List<Long> list = null;

        assertThrows(IllegalArgumentException.class,
                () -> productService.checkProducts(list));

        verifyNoInteractions(productRepository);
        verifyNoInteractions(productMapper);
    }

    @Test
    @DisplayName("checkProducts should return empty list when ID list is empty")
    public void checkProducts_shouldReturnEmptyList_whenIdListIsEmpty() {
        List<Long> emptyIdList = Collections.emptyList();

        List<ProductCheckDto> result = productService.checkProducts(emptyIdList);

        assertThat(result).isNotNull().isEmpty();

        verifyNoInteractions(productRepository);
    }

}