package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
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

    @GetMapping("/hello")
    public String hello(){
        return "<b>Wyszukiwarka Czasopism Naukowych</b> <br> <i>Politechnika Warszawska</i>";
    }

    //TODO What we need?
    //   + Find all all
    //   + Find journal by id
    //   + Find journal by title/issn/eissn/
    //   Find journal by category
    //   + Find user by id
    //   + Find user by name/surname/email
    //   Find all journal by group/user
    //   + Add new User
    //   + Add new journals
    //   + Add new group
    //   + Add user to group
    //   + Add journal to group
    //   + Remove journal
    //   + Remove category
    //   + Remove user
    //   + Remove user from group
    //   + Remove journal from group
    //
    //   Add when save User, add group

    //---FindAll--------------------------------------------------------------------------------------------------

    @GetMapping("/allScientificJournal")
    public String findAllScientificJournal(@RequestParam("sort") String sort, @RequestParam("column") String column){
        List<ScientificJournal> data = scientificJournalService.findAll(column);
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

    //---Save---------------------------------------------------------------------------------------------------

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user){
        return userService.save(user);
    }

    @PostMapping("/saveCategory")
    public Category saveCategory(@RequestBody Category category){
        return categoryService.save(category);
    }

    @PostMapping("/saveGroup")
    public Group saveGroup(@RequestBody Group group){
        return groupService.save(group);
    }

    @PostMapping("/saveScientificJournal")
    public ScientificJournal saveScientificJournal(@RequestBody ScientificJournal scientificJournal){
        return scientificJournalService.save(scientificJournal);
    }

    //---FindBy---------------------------------------------------------------------------------------------------

    @GetMapping("/findJournalById")
    public String findScientificJournal(@RequestParam("id") int id) throws ResourceNotFoundException {
        ScientificJournal data = scientificJournalService.findById(id);
        if (data == null){
            return "Not found.";
        }
        return data.toString();
    }

    @GetMapping("/findAllJournalsByIds")
    public List<ScientificJournal> findScientificJournalsByIds(@RequestParam("ids") List<Integer> ids, @RequestParam("column") String column){
        return scientificJournalService.findAllById(ids, column);
    }

    @GetMapping("/findAllJournalsByTitle")
    public List<ScientificJournal> findScientificJournalsByTitle(@RequestParam("title") String title, @RequestParam("column") String column){
        return scientificJournalService.findAllByTitle(title, column);
    }

    @GetMapping("/findAllJournalsByIssn")
    public List<ScientificJournal> findScientificJournalsByIssn(@RequestParam("issn") String issn, @RequestParam("column") String column){
        return scientificJournalService.findAllByIssn(issn, column);
    }

    @GetMapping("/findAllJournalsByEissn")
    public List<ScientificJournal> findScientificJournalsByEissn(@RequestParam("eissn") String eissn, @RequestParam("column") String column){
        return scientificJournalService.findAllByEissn(eissn, column);
    }

    @GetMapping("/findUserById")
    public User userById(@RequestParam("id") int id) throws ResourceNotFoundException {
        return userService.findById(id);
    }

    @GetMapping("/findUser")
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

    @GetMapping("/findCategoryById")
    public Category findCategoryById(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        return categoryService.findById(id);
    }

    @GetMapping("/findAllCategoryByIds")
    public List<Category> findAllCategoryByIds(@RequestParam List<Integer> ids){
        return categoryService.findAllById(ids);
    }

    @GetMapping("/findGroupById")
    public Group findGroupById(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        return groupService.findById(id);
    }

    @GetMapping("/findJournalsByGroup")
    public List<ScientificJournal> findJournalsByGroup(@RequestParam("groupId") Integer groupId) throws ResourceNotFoundException {
        return groupService.findJournalsByGroup(groupId);
    }

    @GetMapping("/findGroupsByUser")
    public List<Group> findAllGroupsByUser(@RequestParam("userId") Integer userId) throws ResourceNotFoundException {
        return groupService.findAllByUser(userId);
    }

    //---Delete---------------------------------------------------------------------------------------------------

    @GetMapping("/deleteUser")
    public void deleteUser(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        userService.delete(id);
    }

    @GetMapping("/deleteCategory")
    public void deleteCategory(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        categoryService.delete(id);
    }

    @GetMapping("/deleteScientificJournal")
    public void deleteScientificJournal(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        scientificJournalService.delete(id);
    }

    //---GroupManipulateList----------------------------------------------------------------------------------------

    @GetMapping("/addJournalToGroup")
    public Group addJournalToGroup(@RequestParam("groupId") Integer groupId, @RequestParam("journalId") Integer journalId) throws ResourceNotFoundException {
        return groupService.addJournal(journalId, groupId);
    }

    @GetMapping("/removeJournalFromGroup")
    public void removeJournalFromGroup(@RequestParam("groupId") Integer groupId, @RequestParam("journalId") Integer journalId) throws ResourceNotFoundException {
        groupService.removeJournal(journalId, groupId);
    }

    @GetMapping("/addUserToGroup")
    public Group addUserToGroup(@RequestParam("groupId") Integer groupId, @RequestParam("userId") Integer userId) throws ResourceNotFoundException {
        return groupService.addUser(userId, groupId);
    }

    @GetMapping("/removeUserFromGroup")
    public void removeUserFromGroup(@RequestParam("groupId") Integer groupId, @RequestParam("userId") Integer userId) throws ResourceNotFoundException {
        groupService.removeUser(userId, groupId);
    }
}
