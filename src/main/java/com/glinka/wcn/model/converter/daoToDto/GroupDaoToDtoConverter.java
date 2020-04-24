package com.glinka.wcn.model.converter.daoToDto;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.GroupDao;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class GroupDaoToDtoConverter extends ConverterAdapter<Group, GroupDao> {

    private final UserService userService;
    private final ScientificJournalService scientificJournalService;

    public GroupDaoToDtoConverter(UserService userService, ScientificJournalService scientificJournalService) {
        this.userService = userService;
        this.scientificJournalService = scientificJournalService;
    }

    @Override
    public Group convert(Group target, GroupDao source) {

        if (target == null || source == null)
            return null;

        List<Integer> usersId = new ArrayList<>();
        for(UserDao user : source.getUsers()){
            usersId.add(user.getId());
        }
        List<Integer> journalId = new ArrayList<>();
        for(ScientificJournalDao journalDao : source.getJournalDaos()){
            journalId.add(journalDao.getId());
        }

        target.setId(source.getId());
        target.setName(source.getName());
        target.setUsers(userService.findAllById(usersId));
        target.setJournals(scientificJournalService.findAllById(journalId));

        return target;
    }
}
