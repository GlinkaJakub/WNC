package com.glinka.wcn.service.impl;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.repository.CategoryRepository;
import com.glinka.wcn.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ConverterAdapter<Category, CategoryDao> categoryDaoToDtoConverter;
    private final ConverterAdapter<CategoryDao, Category> categoryDtoToDaoConverter;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ConverterAdapter<Category, CategoryDao> categoryDaoToDtoConverter, ConverterAdapter<CategoryDao, Category> categoryDtoToDaoConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryDaoToDtoConverter = categoryDaoToDtoConverter;
        this.categoryDtoToDaoConverter = categoryDtoToDaoConverter;
    }

    @Override
    public List<Category> findAll() {
        return categoryDaoToDtoConverter.convertToList(categoryRepository.findAll());
    }

    @Override
    public List<Category> findAllById(List<Integer> ids) {
        return categoryDaoToDtoConverter.convertToList(categoryRepository.findAllById(ids));
    }

    @Override
    public List<CategoryDao> findAllDaoById(List<Integer> ids) {
        return categoryRepository.findAllById(ids);
    }

    @Override
    public Category findById(int id) {
        return categoryDaoToDtoConverter.convert(categoryRepository.findById(id).orElse(null));
    }

    @Override
    public boolean save(Category category) {
        categoryRepository.saveAndFlush(categoryDtoToDaoConverter.convert(category));
        return true;
    }
}
