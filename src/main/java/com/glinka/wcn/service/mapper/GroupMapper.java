package com.glinka.wcn.service.mapper;

import com.glinka.wcn.model.dao.Group;
import com.glinka.wcn.model.dao.Journal;
import com.glinka.wcn.model.dao.User;
import com.glinka.wcn.model.dto.GroupDto;
import com.glinka.wcn.model.dto.ScientificJournalDto;
import com.glinka.wcn.model.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class GroupMapper implements Mapper<GroupDto, Group> {

    private final Mapper<UserDto, User> userMapper;
    private final Mapper<ScientificJournalDto, Journal> journalsMapper;

    @Autowired
    public GroupMapper(Mapper<UserDto, User> userMapper, Mapper<ScientificJournalDto, Journal> journalsMapper) {
        this.userMapper = userMapper;
        this.journalsMapper = journalsMapper;
    }

    @Override
    public GroupDto mapToDto(Group group) {
        return GroupDto.builder()
                .id(group.getGroupId())
                .name(group.getName())
                .journals(group.getJournals().stream().map(journalsMapper::mapToDto).collect(Collectors.toList()))
                .userDtos(group.getUsers().stream().map(userMapper::mapToDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Group mapToDao(GroupDto groupDto) {
        return Group.builder()
                .groupId(groupDto.getId())
                .name(groupDto.getName())
                .journals(groupDto.getJournals().stream().map(journalsMapper::mapToDao).collect(Collectors.toList()))
                .users(groupDto.getUserDtos().stream().map(userMapper::mapToDao).collect(Collectors.toList()))
                .build();
    }
}
