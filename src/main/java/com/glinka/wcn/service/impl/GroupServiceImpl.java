package com.glinka.wcn.service.impl;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.GroupDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.repository.GroupRepository;
import com.glinka.wcn.service.GroupService;

import java.util.List;

public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final ConverterAdapter<Group, GroupDao> groupDaoToDtoConverter;

    public GroupServiceImpl(GroupRepository groupRepository, ConverterAdapter<Group, GroupDao> groupDaoToDtoConverter) {
        this.groupRepository = groupRepository;
        this.groupDaoToDtoConverter = groupDaoToDtoConverter;
    }

    @Override
    public List<Group> findAll() {
        return groupDaoToDtoConverter.convertToList(groupRepository.findAll());
    }

    @Override
    public Group findById(int id) {
        return groupDaoToDtoConverter.convert(groupRepository.findById(id).orElse(null));
    }

    @Override
    public boolean save(Group group) {
        return false;
    }

    @Override
    public boolean addJournal(ScientificJournal scientificJournal, Group group) {
        return false;
    }

    @Override
    public boolean addUser(User user, Group group) {
        return false;
    }

    @Override
    public boolean removeUser(User user, Group group) {
        return false;
    }

    @Override
    public boolean removeJournal(ScientificJournal scientificJournal, Group group) {
        return false;
    }
}
