package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;

import java.util.List;


public interface GroupService {

    List<Group> findAll();
    Group findById(Integer id) throws ResourceNotFoundException;
    List<Group> findAllByIds(List<Integer> ids);
    //   Add new group
    Group save(Group group);
    //   Add journal to group
    Group addJournal(Integer scientificJournalId, Integer groupId) throws ResourceNotFoundException ;
    //   Add user to group
    Group addUser(Integer userId, Integer groupId) throws ResourceNotFoundException ;
    //   Remove user from group
    void removeUser(Integer userId, Integer groupId) throws ResourceNotFoundException;
    //   Remove journal from group
    void removeJournal(Integer scientificJournalId, Integer groupId) throws ResourceNotFoundException;
    //   Find Journals by group
    List<ScientificJournal> findJournalsByGroup(Integer groupId) throws ResourceNotFoundException;
    // find all groups bu user
    List<Group> findAllByUser(Integer userId) throws ResourceNotFoundException;


}
