package com.glinka.wcn.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() throws Exception {
        MvcResult login = mockMvc.perform(post("/login")
            .content("{\"email\": \"jakub@wp.pl\", \"password\": \"haslo123\"}")
        )
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();

        String token = login.getResponse().getHeader("Authorization");

        mockMvc.perform(get("/secured")
        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(content().string("secured"));

        mockMvc.perform(get("/secured"))
                .andDo(print())
                .andExpect(status().is(401));
    }
}