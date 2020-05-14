package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.Category;

import java.util.List;


public interface CategoryService {

    List<Category> findAll();
    List<Category> findAllById(List<Integer> ids);
    Category findById(Integer id) throws ResourceNotFoundException;
    Category save(Category category);
    void delete(Integer id) throws ResourceNotFoundException;

}
