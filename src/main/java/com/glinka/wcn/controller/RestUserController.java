package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.UserDto;
import com.glinka.wcn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public UserDto saveUser(@RequestBody UserDto userDto){
        return userService.save(userDto);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.delete(id);
    }

    @GetMapping("/users")
    public List<UserDto> findAllUsers(){
        List<UserDto> data = userService.findAll();
        if (data == null || data.isEmpty()){
            return Collections.emptyList();
        }
        return data;
        //TODO("change response from Srting to List")
    }

    @GetMapping("/users/{id}")
    public UserDto userById(@PathVariable Long id) throws ResourceNotFoundException {
        return userService.findById(id);
    }

    @GetMapping("/users/names/{user}")
    public List<UserDto> findUser(@PathVariable String user){
        return userService.findAllByNameOrSurname(user);
    }


    @GetMapping("/users/emails/{email}")
    public UserDto findUserByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/users/ids")
    public List<UserDto> findAllUsersByIds(@RequestParam List<Long> ids){
        return userService.findAllById(ids);
    }

}
