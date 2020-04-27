package com.glinka.wcn.service;

import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;

import java.util.List;


public interface GroupService {

    List<Group> findAll();
    Group findById(int id);
    //   Add new group
    boolean save(Group group);
    //   Add journal to group
    boolean addJournal(ScientificJournal scientificJournal, Group group);
    //   Add user to group
    boolean addUser(User user,Group group);
    //   Remove user from group
    boolean removeUser(User user, Group group);
    //   Remove journal from group
    boolean removeJournal(ScientificJournal scientificJournal, Group group);

}
