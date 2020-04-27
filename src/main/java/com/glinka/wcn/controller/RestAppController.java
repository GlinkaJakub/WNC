package com.glinka.wcn.controller;

import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestAppController {

    private final ScientificJournalService scientificJournalService;
    private final UserService userService;
    private final GroupService groupService;
    private final CategoryService categoryService;

    public RestAppController(ScientificJournalService scientificJournalService, UserService userService, GroupService groupService, CategoryService categoryService) {
        this.scientificJournalService = scientificJournalService;
        this.userService = userService;
        this.groupService = groupService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String hello(){
        return "<b>Wyszukiwarka Czasopism Naukowych</b> <br> <i>Politechnika Warszawska</i>";
    }

    //TODO What we need?
    //   + Find all all
    //   + Find journal by id
    //   Find journal by title/issn/eissn/category
    //   + Find user by id
    //   Find user by name/surname/email
    //   Find journal by group/user
    //   + Add new User
    //   Add new journals
    //   Add new group
    //   Add user to group
    //   Add journal to group
    //   Remove journal
    //   Remove user from group
    //   Remove journal from group

    @GetMapping("/allScientificJournal")
    public String findAllScientificJournal(){
        List<ScientificJournal> data = scientificJournalService.findAll();
        if(data == null || data.isEmpty()){
            return "Not found data";
        }
        return data.toString();
    }

    @GetMapping("/allUsers")
    public String findAllUsers(){
        List<User> data = userService.findAll();
        if (data == null || data.isEmpty()){
            return "Not found data";
        }
        return data.toString();
    }

    @GetMapping("/allCategory")
    public String findAllCategory(){
        List<Category> data = categoryService.findAll();
        if (data == null || data.isEmpty()){
            return "Not found data";
        }
        return data.toString();
    }

    @GetMapping("/allGroup")
    public String findAllGroup(){
        List<Group> data = groupService.findAll();
        if (data == null || data.isEmpty()){
            return "Not found data";
        }
        return data.toString();
    }

    //------------------------------------------------------------------------------------------------------

    @PostMapping("/saveUser")
    public boolean saveUser(@RequestBody User user){
        return userService.save(user);
    }

    @PostMapping("/saveCategory")
    public boolean saveCategory(@RequestBody Category category){
        return categoryService.save(category);
    }

    @PostMapping("/saveGroup")
    public boolean saveGroup(@RequestBody Group group){
        return groupService.save(group);
    }

    @PostMapping("/saveScientificJournal")
    public boolean saveScientificJournal(@RequestBody ScientificJournal scientificJournal){
        return scientificJournalService.save(scientificJournal);
    }

    //------------------------------------------------------------------------------------------------------

    @GetMapping("/findJournalById")
    public String findScientificJournal(@RequestParam("id") int id){
        ScientificJournal data = scientificJournalService.findById(id);
        if (data == null){
            return "Not found.";
        }
        return data.toString();
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
