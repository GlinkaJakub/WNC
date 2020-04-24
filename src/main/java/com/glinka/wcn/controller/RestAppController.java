package com.glinka.wcn.controller;

import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestAppController {

    private ScientificJournalService scientificJournalService;
    private UserService userService;
    private GroupService groupService;
    private CategoryService categoryService;

    @GetMapping("/")
    public String hello(){
        return "<b>Wyszukiwarka Czasopism Naukowych</b> <br> <i>Politechnika Warszawska</i>";
    }

    //TODO What we need?
    //   Show all journals
    //   Find journal by id/title/issn/eissn/category
    //   Find user by id/name/surname/email
    //   Find journal by group/user
    //   Add new User
    //   Add new journals
    //   Add new group
    //   Add user to group
    //   Add journal to group
    //   Remove journal
    //   Remove user from group
    //   Remove journal from group

    @GetMapping("/allScientificJournal")
    public List<ScientificJournal> findAllScientificJournal(){
        return scientificJournalService.findAll();
    }

    @GetMapping("/findJournalById")
    public ScientificJournal findScientificJournal(@RequestParam("id") int id){
        return scientificJournalService.findById(id);
    }

    @GetMapping("/findUserById")
    public User userById(@RequestParam("id") int id){
        return userService.findById(id);
    }

    @GetMapping("/findUser")
    public List<User> findUser(@RequestParam("user") String user){
        return userService.findAllByNameOrSurname(user);
    }






}
