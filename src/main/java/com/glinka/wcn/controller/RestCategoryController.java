package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.CategoryDto;
import com.glinka.wcn.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestCategoryController {

    private final CategoryService categoryService;

    @Autowired
    public RestCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<CategoryDto> findAllCategory(){
        List<CategoryDto> data = categoryService.findAll();
        if (data == null || data.isEmpty()){
            return Collections.emptyList();
        }
        return data;
    }

    @PostMapping("/categories")
    public CategoryDto saveCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.save(categoryDto);
    }

    @DeleteMapping("/categories/{id}")
    public void deleteCategory(@PathVariable Long id) throws ResourceNotFoundException {
        categoryService.delete(id);
    }

    @GetMapping("/categories/ids")
    public List<CategoryDto> findAllCategoryByIds(@RequestParam List<Long> ids){
        return categoryService.findAllById(ids);
    }

    @GetMapping("/categories/{id}")
    public CategoryDto findCategoryById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return categoryService.findById(id);
    }

    //TODO update category
}
