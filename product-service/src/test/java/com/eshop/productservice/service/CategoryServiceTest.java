package com.eshop.productservice.service;

import com.eshop.productservice.dto.CategoryDto;
import com.eshop.productservice.mappers.CategoryMapper;
import com.eshop.productservice.model.Category;
import com.eshop.productservice.repository.CategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("findAllCategories should return correct <D")
    public void findAllCategoriesShouldReturnCorrectCategoriesList() {
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("c1");

        Category c2 = new Category();
        c2.setId(2L);
        c2.setName("c2");

        CategoryDto dto1 = new CategoryDto(c1.getId(), c1.getName());
        CategoryDto dto2 = new CategoryDto(c2.getId(), c2.getName());

        when(repository.findAll()).thenReturn(List.of(c1, c2));

        when(categoryMapper.toDto(c1)).thenReturn(dto1);
        when(categoryMapper.toDto(c2)).thenReturn(dto2);

        List<CategoryDto> allCategories = categoryService.findAllCategories();

        assertThat(allCategories).isNotNull();
        assertThat(allCategories).hasSize(2);
        assertThat(allCategories).containsExactlyInAnyOrder(dto1, dto2);
    }
}