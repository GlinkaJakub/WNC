package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.Category;
import com.glinka.wcn.model.dto.CategoryDto;
import com.glinka.wcn.repository.CategoryRepository;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final Mapper<CategoryDto, Category> categoryMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(Mapper<CategoryDto, Category> categoryMapper, CategoryRepository categoryRepository) {
        this.categoryMapper = categoryMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        return categoryMapper.mapToDto(categoryRepository.saveAndFlush(categoryMapper.mapToDao(categoryDto)));
    }

    @Override
    public CategoryDto findById(Long id) throws ResourceNotFoundException {
        Optional<Category> categoryDao = categoryRepository.findById(id);
        return categoryMapper.mapToDto(categoryDao.orElseThrow(
                () -> new ResourceNotFoundException("Category with id: " + id + " not found")
        ));
    }

    @Override
    public List<CategoryDto> findAll(){
        return categoryRepository.findAll().stream().map(categoryMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException{
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> findAllById(List<Long> ids) {
        List<Category> categoryList = categoryRepository.findAllById(ids);
        return categoryList.stream().map(categoryMapper::mapToDto).collect(Collectors.toList());
    }
}
