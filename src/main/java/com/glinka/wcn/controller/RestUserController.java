package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class RestUserController {

    private final ScientificJournalService scientificJournalService;
    private final UserService userService;
    private final GroupService groupService;
    private final CategoryService categoryService;

    public RestUserController(ScientificJournalService scientificJournalService, UserService userService, GroupService groupService, CategoryService categoryService) {
        this.scientificJournalService = scientificJournalService;
        this.userService = userService;
        this.groupService = groupService;
        this.categoryService = categoryService;
    }

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/deleteUser")
    public void deleteUser(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        userService.delete(id);
    }

    @GetMapping("/allUsers")
    public List<User> findAllUsers(){
        List<User> data = userService.findAll();
        if (data == null || data.isEmpty()){
            return Collections.emptyList();
        }
        return data;
        //TODO("change response from Srting to List")
    }

    @GetMapping("/findUserById")
    public User userById(@RequestParam("id") int id) throws ResourceNotFoundException {
        return userService.findById(id);
    }

    @GetMapping("/findUserByName")
    public List<User> findUser(@RequestParam("user") String user){
        return userService.findAllByNameOrSurname(user);
    }


    @GetMapping("/findUserByEmail")
    public User findUserByEmail(@RequestParam("user") String email){
        return userService.findByEmail(email);
    }

    @GetMapping("/findAllUsersByIds")
    public List<User> findAllUsersByIds(@RequestParam List<Integer> ids){
        return userService.findAllById(ids);
    }

}
