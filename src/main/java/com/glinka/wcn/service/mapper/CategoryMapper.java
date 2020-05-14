package com.glinka.wcn.service.mapper;

import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dto.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper implements Mapper<Category, CategoryDao> {

    @Override
    public Category mapToDto(CategoryDao categoryDao) {
        return Category.builder()
                .id(categoryDao.getId())
                .name(categoryDao.getName())
                .build();
    }

    @Override
    public CategoryDao mapToDao(Category category) {
        return CategoryDao.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
