package com.glinka.wcn;

import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryIntegrationTests extends BaseIntegrationTests {

    @Autowired
    CategoryService categoryService;

    @Test
    public void testGetAllCategory(){
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        Category category1 = new Category(1, "computer science");
        Category category2 = new Category(2, "it");
        categoryService.save(category1);
        categoryService.save(category2);
        ResponseEntity<Category[]> response = restTemplate.exchange(
                createURLWithPort("/api/category/allCategory"),
                HttpMethod.GET,
                entity,
                Category[].class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
    }

    // saveCategory
    @Test
    public void testSaveCategory(){
        HttpEntity<Category> entity = new HttpEntity<>(new Category(1, "computer science"), headers);
        ResponseEntity<Category> response = restTemplate.exchange(
                createURLWithPort("/api/category/saveCategory"),
                HttpMethod.POST,
                entity,
                Category.class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("computer science", response.getBody().getName());
    }
    // deleteCategory

    @Test
    public void testDeleteCategory(){
        HttpEntity<Category> entity = new HttpEntity<>(null, headers);
        int id = 1;
        categoryService.save(new Category(1, "category"));
        categoryService.save(new Category(2, "second Category"));

        ResponseEntity<Category> response = restTemplate.exchange(
                createURLWithPort("/api/category/deleteCategory?id={id}"),
                HttpMethod.GET,
                entity,
                Category.class,
                id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, categoryService.findAll().size());
    }
    // findAllCategoryByIds

    @Test
    public void testFindAllCategoryByIds(){
        HttpEntity<Category> entity = new HttpEntity<>(null, headers);
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(3);
        String idsToString = idsToString(ids);
        Category category1 = new Category(1, "category1");
        Category category2 = new Category(2, "category2");
        Category category3 = new Category(3, "category3");
        Category category4 = new Category(4, "category4");
        categoryService.save(category1);
        categoryService.save(category2);
        categoryService.save(category3);
        categoryService.save(category4);

        ResponseEntity<Category[]> response = restTemplate.exchange(
                createURLWithPort("api/category/findAllCategoryByIds?ids={idsToString}"),
                HttpMethod.GET,
                entity,
                Category[].class,
                idsToString
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(category1, response.getBody()[0]);
        assertEquals(category3, response.getBody()[1]);
        assertEquals(1, response.getBody()[0].getId());
        assertEquals("category1", response.getBody()[0].getName());
    }
    // findCategoryById

    @Test
    public void testFindCategoryById(){
        HttpEntity<Category> entity = new HttpEntity<>(null, headers);
        int id = 2;
        Category category1 = new Category(1, "first");
        Category category2 = new Category(2, "second");
        categoryService.save(category1);
        categoryService.save(category2);

        ResponseEntity<Category> response = restTemplate.exchange(
                createURLWithPort("/api/category/findCategoryById?id={id}"),
                HttpMethod.GET,
                entity,
                Category.class,
                id
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getId());
        assertEquals("second", response.getBody().getName());
    }
}
