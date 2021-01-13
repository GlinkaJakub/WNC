package com.glinka.wcn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glinka.wcn.model.dao.Category;
import com.glinka.wcn.controller.dto.CategoryDto;
import com.glinka.wcn.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
class RestCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @Transactional
    void findAllCategory() throws Exception {
        //given
        Category newCategory = Category.builder()
                .name("test")
                .build();
        categoryRepository.save(newCategory);
        //when
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/categories/" + newCategory.getCategoryId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        //then
        CategoryDto category = objectMapper.readValue(result.getResponse().getContentAsString(), CategoryDto.class);
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(newCategory.getCategoryId());
        assertThat(category.getName()).isEqualTo(newCategory.getName());
    }
}