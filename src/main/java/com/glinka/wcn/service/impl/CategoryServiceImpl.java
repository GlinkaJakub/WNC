package com.glinka.wcn.service.impl;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.repository.CategoryRepository;
import com.glinka.wcn.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    private ConverterAdapter<Category, CategoryDao> categoryDaoToDtoConverter;

    @Override
    public List<Category> findAll() {
        return categoryDaoToDtoConverter.convertToList(categoryRepository.findAll());
    }

    @Override
    public Category findById(int id) {
        return categoryDaoToDtoConverter.convert(categoryRepository.findById(id).orElse(null));
    }
}
