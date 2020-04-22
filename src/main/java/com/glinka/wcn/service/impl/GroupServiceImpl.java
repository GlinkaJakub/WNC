package com.glinka.wcn.service.impl;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.GroupDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.repository.GroupRepository;
import com.glinka.wcn.service.GroupService;

import java.util.List;

public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    private ConverterAdapter<Group, GroupDao> groupDaoToDtoConverter;

    @Override
    public List<Group> findAll() {
        return groupDaoToDtoConverter.convertToList(groupRepository.findAll());
    }

    @Override
    public Group findById(int id) {
        return groupDaoToDtoConverter.convert(groupRepository.findById(id).orElse(null));
    }
}
