package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
public class RestGroupController {

    private final ScientificJournalService scientificJournalService;
    private final UserService userService;
    private final GroupService groupService;
    private final CategoryService categoryService;

    public RestGroupController(ScientificJournalService scientificJournalService, UserService userService, GroupService groupService, CategoryService categoryService) {
        this.scientificJournalService = scientificJournalService;
        this.userService = userService;
        this.groupService = groupService;
        this.categoryService = categoryService;
    }

    @GetMapping("/allGroup")
    public String findAllGroup(){
        List<Group> data = groupService.findAll();
        if (data == null || data.isEmpty()){
            return "Not found data";
        }
        return data.toString();
    }

    @PostMapping("/saveGroup")
    public Group saveGroup(@RequestBody Group group){
        return groupService.save(group);
    }

    @GetMapping("/findGroupsByUser")
    public List<Group> findAllGroupsByUser(@RequestParam("userId") Integer userId) throws ResourceNotFoundException {
        return groupService.findAllByUser(userId);
    }

    @GetMapping("/findGroupById")
    public Group findGroupById(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        return groupService.findById(id);
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
