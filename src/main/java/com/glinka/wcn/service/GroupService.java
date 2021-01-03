package com.glinka.wcn.service;

import com.glinka.wcn.commons.NotAuthorizedException;
import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.GroupDto;
import com.glinka.wcn.model.dto.GroupNameDto;
import com.glinka.wcn.model.dto.ScientificJournalDto;

import java.util.List;


public interface GroupService {

    List<GroupDto> findAll();
    GroupDto findById(Long id) throws ResourceNotFoundException;
    List<GroupDto> findAllByIds(List<Long> ids);
    //   Add new group
    GroupDto save(GroupNameDto groupDto, String email);
    //   Add journal to group
    GroupDto addJournal(Long scientificJournalId, Long groupId) throws ResourceNotFoundException ;
    //   Add user to group
    GroupDto addUser(String owner, String newUser, Long groupId) throws ResourceNotFoundException, NotAuthorizedException;
    //   Remove user from group
    void removeUser(String owner, Long userId, Long groupId) throws ResourceNotFoundException, NotAuthorizedException;
    //   Remove journal from group
    void removeJournal(Long scientificJournalId, Long groupId) throws ResourceNotFoundException;
    //   Find Journals by group
    List<ScientificJournalDto> findJournalsByGroup(Long groupId) throws ResourceNotFoundException;
    // find all groups bu user
    List<GroupDto> findAllByUser(String email) throws ResourceNotFoundException;
    List<GroupNameDto> findAllGroupsNameByUser(String email) throws ResourceNotFoundException;
    // delete group
    void deleteGroup(String owner, Long id) throws ResourceNotFoundException, NotAuthorizedException;


}
