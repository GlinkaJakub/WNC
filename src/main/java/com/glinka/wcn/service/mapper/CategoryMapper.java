package com.glinka.wcn.service.mapper;

import com.glinka.wcn.model.dao.Category;
import com.glinka.wcn.controller.dto.CategoryDto;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper implements Mapper<CategoryDto, Category> {

    @Override
    public CategoryDto mapToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getCategoryId())
                .name(category.getName())
                .build();
    }

    @Override
    public Category mapToDao(CategoryDto categoryDto) {
        return Category.builder()
                .categoryId(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }
}
