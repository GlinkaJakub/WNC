package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.CategoryDto;

import java.util.List;


public interface CategoryService {

    List<CategoryDto> findAll();
    List<CategoryDto> findAllById(List<Long> ids);
    CategoryDto findById(Long id) throws ResourceNotFoundException;
    CategoryDto save(CategoryDto categoryDto);
    void delete(Long id) throws ResourceNotFoundException;

}
