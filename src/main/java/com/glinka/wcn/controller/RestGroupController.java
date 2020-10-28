package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.GroupDto;
import com.glinka.wcn.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public ResponseEntity<List<GroupDto>> findAllGroup(){
        List<GroupDto> data = groupService.findAll();
        if (data == null || data.isEmpty()){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/groups")
    public ResponseEntity<GroupDto> saveGroup(@RequestBody @Valid GroupDto groupDto){
        return new ResponseEntity<>(groupService.save(groupDto), HttpStatus.CREATED);
    }

    @GetMapping("/users/{userId}/groups")
    public ResponseEntity<List<GroupDto>> findAllGroupsByUser(@PathVariable Long userId) throws ResourceNotFoundException {
        return new ResponseEntity<>(groupService.findAllByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/groups/{id}")
    public ResponseEntity<GroupDto> findGroupById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(groupService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/groups/{groupId}/journals/{journalId}")
    public ResponseEntity<GroupDto> addJournalToGroup(@PathVariable Long groupId, @PathVariable Long journalId) throws ResourceNotFoundException {
        return new ResponseEntity<>(groupService.addJournal(journalId, groupId), HttpStatus.OK);
    }

    @DeleteMapping("/groups/{groupId}/journals/{journalId}")
    public ResponseEntity<String> removeJournalFromGroup(@PathVariable Long groupId, @PathVariable Long journalId) throws ResourceNotFoundException {
        groupService.removeJournal(journalId, groupId);
        return new ResponseEntity<>("Delete journal with id: " + journalId + " from group with id: " + groupId, HttpStatus.OK);
    }

    @GetMapping("/groups/{groupId}/users/{userId}")
    public ResponseEntity<GroupDto> addUserToGroup(@PathVariable Long groupId, @PathVariable Long userId) throws ResourceNotFoundException {
        return new ResponseEntity<>(groupService.addUser(userId, groupId), HttpStatus.OK);
    }

    @DeleteMapping("/groups/{groupId}/users/{userId}")
    public ResponseEntity<String> removeUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId) throws ResourceNotFoundException {
        groupService.removeUser(userId, groupId);
        return new ResponseEntity<>("Delete user with id: " + userId + " from group with id: " + groupId, HttpStatus.OK);

    }

    @DeleteMapping("/groups/{groupId}")
    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) throws ResourceNotFoundException{
        groupService.deleteGroup(groupId);
        return new ResponseEntity<>("Delete group with id: " + groupId, HttpStatus.OK);
    }

    //TODO update group
}
