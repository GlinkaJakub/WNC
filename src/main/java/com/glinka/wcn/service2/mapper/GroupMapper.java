package com.glinka.wcn.service2.mapper;

import com.glinka.wcn.model.dao.GroupDao;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GroupMapper implements Mapper<Group, GroupDao> {

    private final Mapper<User, UserDao> userMapper;
    private final Mapper<ScientificJournal, ScientificJournalDao> journalsMapper;

    public GroupMapper(Mapper<User, UserDao> userMapper, Mapper<ScientificJournal, ScientificJournalDao> journalsMapper) {
        this.userMapper = userMapper;
        this.journalsMapper = journalsMapper;
    }

    @Override
    public Group mapToDto(GroupDao groupDao) {
        return Group.builder()
                .id(groupDao.getId())
                .name(groupDao.getName())
                .journals(groupDao.getJournalDaos().stream().map(journalsMapper::mapToDto).collect(Collectors.toList()))
                .users(groupDao.getUsers().stream().map(userMapper::mapToDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public GroupDao mapToDao(Group group) {
        return GroupDao.builder()
                .id(group.getId())
                .name(group.getName())
                .journalDaos(group.getJournals().stream().map(journalsMapper::mapToDao).collect(Collectors.toList()))
                .users(group.getUsers().stream().map(userMapper::mapToDao).collect(Collectors.toList()))
                .build();
    }
}
