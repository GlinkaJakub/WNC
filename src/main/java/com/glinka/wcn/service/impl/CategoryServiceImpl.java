package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.repository.CategoryRepository;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final Mapper<Category, CategoryDao> categoryMapper;
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(Mapper<Category, CategoryDao> categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) {
        return categoryMapper.mapToDto(categoryRepository.saveAndFlush(categoryMapper.mapToDao(category)));
    }

    @Override
    public Category findById(Integer id) throws ResourceNotFoundException {
        Optional<CategoryDao> categoryDao = categoryRepository.findById(id);
        return categoryMapper.mapToDto(categoryDao.orElseThrow(
                () -> new ResourceNotFoundException("Category with id: " + id + " not found")
        ));
    }

    @Override
    public List<Category> findAll(){
        return categoryRepository.findAll().stream().map(categoryMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException{
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> findAllById(List<Integer> ids) {
        List<CategoryDao> categoryDaoList = categoryRepository.findAllById(ids);
        return categoryDaoList.stream().map(categoryMapper::mapToDto).collect(Collectors.toList());
    }
}
