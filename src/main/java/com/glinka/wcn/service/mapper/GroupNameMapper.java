package com.glinka.wcn.service.mapper;

import com.glinka.wcn.model.dao.Group;
import com.glinka.wcn.model.dto.GroupNameDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GroupNameMapper implements Mapper<GroupNameDto, Group> {

    @Override
    public GroupNameDto mapToDto(Group group) {
        return GroupNameDto.builder()
                .id(group.getGroupId())
                .name(group.getName())
                .build();
    }

    @Override
    public Group mapToDao(GroupNameDto groupDto) {
        return Group.builder()
                .groupId(groupDto.getId())
                .name(groupDto.getName())
                .journals(new ArrayList<>())
                .users(new ArrayList<>())
                .owner(null)
                .build();
    }
}
