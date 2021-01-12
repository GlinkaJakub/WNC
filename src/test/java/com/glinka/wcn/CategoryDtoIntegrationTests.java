package com.glinka.wcn;

import com.glinka.wcn.controller.dto.CategoryDto;
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

public class CategoryDtoIntegrationTests extends BaseIntegrationTests {

    @Autowired
    CategoryService categoryService;

    @Test
    public void testGetAllCategory(){
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        CategoryDto categoryDto1 = new CategoryDto(1, "computer science");
        CategoryDto categoryDto2 = new CategoryDto(2, "it");
        categoryService.save(categoryDto1);
        categoryService.save(categoryDto2);
        ResponseEntity<CategoryDto[]> response = restTemplate.exchange(
                createURLWithPort("/api//categories"),
                HttpMethod.GET,
                entity,
                CategoryDto[].class
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
    }

    // saveCategory
    @Test
    public void testSaveCategory(){
        HttpEntity<CategoryDto> entity = new HttpEntity<>(new CategoryDto(1, "computer science"), headers);
        ResponseEntity<CategoryDto> response = restTemplate.exchange(
                createURLWithPort("/api/categories"),
                HttpMethod.POST,
                entity,
                CategoryDto.class
        );
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("computer science", response.getBody().getName());
    }
    // deleteCategory

    @Test
    public void testDeleteCategory(){
        HttpEntity<CategoryDto> entity = new HttpEntity<>(null, headers);
        int id = 1;
        categoryService.save(new CategoryDto(1, "category"));
        categoryService.save(new CategoryDto(2, "second Category"));

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/categories/{id}"),
                HttpMethod.DELETE,
                entity,
                String.class,
                id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, categoryService.findAll().size());
    }
    // findAllCategoryByIds

    @Test
    public void testFindAllCategoryByIds(){
        HttpEntity<CategoryDto> entity = new HttpEntity<>(null, headers);
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(3);
        String idsToString = idsToString(ids);
        CategoryDto categoryDto1 = new CategoryDto(1, "category1");
        CategoryDto categoryDto2 = new CategoryDto(2, "category2");
        CategoryDto categoryDto3 = new CategoryDto(3, "category3");
        CategoryDto categoryDto4 = new CategoryDto(4, "category4");
        categoryService.save(categoryDto1);
        categoryService.save(categoryDto2);
        categoryService.save(categoryDto3);
        categoryService.save(categoryDto4);

        ResponseEntity<CategoryDto[]> response = restTemplate.exchange(
                createURLWithPort("api/categories/ids?ids={idsToString}"),
                HttpMethod.GET,
                entity,
                CategoryDto[].class,
                idsToString
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(categoryDto1, response.getBody()[0]);
        assertEquals(categoryDto3, response.getBody()[1]);
        assertEquals(1, response.getBody()[0].getId());
        assertEquals("category1", response.getBody()[0].getName());
    }
    // findCategoryById

    @Test
    public void testFindCategoryById(){
        HttpEntity<CategoryDto> entity = new HttpEntity<>(null, headers);
        int id = 2;
        CategoryDto categoryDto1 = new CategoryDto(1, "first");
        CategoryDto categoryDto2 = new CategoryDto(2, "second");
        categoryService.save(categoryDto1);
        categoryService.save(categoryDto2);

        ResponseEntity<CategoryDto> response = restTemplate.exchange(
                createURLWithPort("/api/categories/{id}"),
                HttpMethod.GET,
                entity,
                CategoryDto.class,
                id
        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getId());
        assertEquals("second", response.getBody().getName());
    }
}
