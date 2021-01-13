package com.glinka.wcn.controller;

import com.glinka.wcn.controller.dto.LoginCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
public class LoginController {

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials credentials){
        log.info("User with {} sign in", credentials.getEmail());
    }

    @GetMapping("/secured")
    public String secured(){
        return "secured";
    }
}
