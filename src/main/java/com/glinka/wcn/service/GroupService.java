package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.GroupDto;
import com.glinka.wcn.model.dto.ScientificJournalDto;

import java.util.List;


public interface GroupService {

    List<GroupDto> findAll();
    GroupDto findById(Long id) throws ResourceNotFoundException;
    List<GroupDto> findAllByIds(List<Long> ids);
    //   Add new group
    GroupDto save(GroupDto groupDto);
    //   Add journal to group
    GroupDto addJournal(Long scientificJournalId, Long groupId) throws ResourceNotFoundException ;
    //   Add user to group
    GroupDto addUser(Long userId, Long groupId) throws ResourceNotFoundException ;
    //   Remove user from group
    void removeUser(Long userId, Long groupId) throws ResourceNotFoundException;
    //   Remove journal from group
    void removeJournal(Long scientificJournalId, Long groupId) throws ResourceNotFoundException;
    //   Find Journals by group
    List<ScientificJournalDto> findJournalsByGroup(Long groupId) throws ResourceNotFoundException;
    // find all groups bu user
    List<GroupDto> findAllByUser(Long userId) throws ResourceNotFoundException;
    // delete group
    void deleteGroup(Long id) throws ResourceNotFoundException;


}
