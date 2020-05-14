package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class RestCategoryController {

    private final ScientificJournalService scientificJournalService;
    private final UserService userService;
    private final GroupService groupService;
    private final CategoryService categoryService;

    public RestCategoryController(ScientificJournalService scientificJournalService, UserService userService, GroupService groupService, CategoryService categoryService) {
        this.scientificJournalService = scientificJournalService;
        this.userService = userService;
        this.groupService = groupService;
        this.categoryService = categoryService;
    }

    @GetMapping("/allCategory")
    public String findAllCategory(){
        List<Category> data = categoryService.findAll();
        if (data == null || data.isEmpty()){
            return "Not found data";
        }
        return data.toString();
    }

    @PostMapping("/saveCategory")
    public Category saveCategory(@RequestBody Category category){
        return categoryService.save(category);
    }

    @GetMapping("/deleteCategory")
    public void deleteCategory(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        categoryService.delete(id);
    }

    @GetMapping("/findAllCategoryByIds")
    public List<Category> findAllCategoryByIds(@RequestParam List<Integer> ids){
        return categoryService.findAllById(ids);
    }

    @GetMapping("/findCategoryById")
    public Category findCategoryById(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        return categoryService.findById(id);
    }

}
