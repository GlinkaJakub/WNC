package com.glinka.wcn.service;

import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dto.Category;

import java.util.List;


public interface CategoryService {

    List<Category> findAll();
    List<Category> findAllById(List<Integer> ids);
    List<CategoryDao> findAllDaoById(List<Integer> ids);
    Category findById(int id);
    boolean save(Category category);

}
