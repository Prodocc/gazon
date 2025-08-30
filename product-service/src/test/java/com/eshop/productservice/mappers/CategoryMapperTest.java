package com.eshop.productservice.mappers;

import com.eshop.productservice.dto.CategoryDto;
import com.eshop.productservice.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CategoryMapperTest {

    private CategoryMapper categoryMapper;

    @BeforeEach
    public void setup() {
        this.categoryMapper = new CategoryMapper();
    }


    @Test
    @DisplayName("categoryToCategoryDtoMappingTest")
    public void shouldMapCategoryToCategoryDto() {
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("my_category");

        CategoryDto dto = categoryMapper.toDto(c1);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(c1.getId());
        assertThat(dto.getName()).isEqualTo(c1.getName());
    }

    @Test
    @DisplayName("nullCategoryToCategoryDtoMappingTest")
    public void shouldMapNullCategoryToCategoryDto() {
        Category c1 = null;

        CategoryDto dto = categoryMapper.toDto(c1);

        assertThat(dto).isNull();
    }

}