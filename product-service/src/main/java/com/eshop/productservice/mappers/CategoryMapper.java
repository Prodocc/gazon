package com.eshop.productservice.mappers;

import com.eshop.productservice.dto.CategoryDto;
import com.eshop.productservice.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category){
        if (category == null){
            return null;
        }

        return new CategoryDto(category.getId(), category.getName());
    }
}
