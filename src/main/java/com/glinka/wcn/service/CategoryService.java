package com.glinka.wcn.service;

import com.glinka.wcn.model.dto.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();
    Category findById(int id);

}