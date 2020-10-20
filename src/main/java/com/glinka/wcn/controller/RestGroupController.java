package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.GroupDto;
import com.glinka.wcn.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestGroupController {

    private final GroupService groupService;

    @Autowired
    public RestGroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/groups")
    public List<GroupDto> findAllGroup(){
        List<GroupDto> data = groupService.findAll();
        if (data == null || data.isEmpty()){
            return Collections.emptyList();
        }
        return data;
    }

    @PostMapping("/groups")
    public GroupDto saveGroup(@RequestBody GroupDto groupDto){
        return groupService.save(groupDto);
    }

    @GetMapping("/users/{userId}/groups")
    public List<GroupDto> findAllGroupsByUser(@PathVariable Long userId) throws ResourceNotFoundException {
        return groupService.findAllByUser(userId);
    }

    @GetMapping("/groups/{id}")
    public GroupDto findGroupById(@PathVariable Long id) throws ResourceNotFoundException {
        return groupService.findById(id);
    }

    @GetMapping("/groups/{groupId}/journals/{journalId}")
    public GroupDto addJournalToGroup(@PathVariable Long groupId, @PathVariable Long journalId) throws ResourceNotFoundException {
        return groupService.addJournal(journalId, groupId);
    }

    @DeleteMapping("/groups/{groupId}/journals/{journalId}")
    public void removeJournalFromGroup(@PathVariable Long groupId, @PathVariable Long journalId) throws ResourceNotFoundException {
        groupService.removeJournal(journalId, groupId);
    }

    @GetMapping("/groups/{groupId}/users/{userId}")
    public GroupDto addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) throws ResourceNotFoundException {
        return groupService.addUser(userId, groupId);
    }

    @DeleteMapping("/groups/{groupId}/users/{userId}")
    public void removeUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId) throws ResourceNotFoundException {
        groupService.removeUser(userId, groupId);
    }
}
