package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.Group;
import com.glinka.wcn.model.dao.Journal;
import com.glinka.wcn.model.dao.User;
import com.glinka.wcn.model.dto.GroupDto;
import com.glinka.wcn.model.dto.ScientificJournalDto;
import com.glinka.wcn.repository.GroupRepository;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final Mapper<GroupDto, Group> groupMapper;
    private final Mapper<ScientificJournalDto, Journal> scientificJournalMapper;
    private final GroupRepository groupRepository;
    private final ScientificJournalRepository scientificJournalRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupServiceImpl(Mapper<GroupDto, Group> groupMapper, Mapper<ScientificJournalDto, Journal> scientificJournalMapper, GroupRepository groupRepository, ScientificJournalRepository scientificJournalRepository, UserRepository userRepository) {
        this.groupMapper = groupMapper;
        this.scientificJournalMapper = scientificJournalMapper;
        this.groupRepository = groupRepository;
        this.scientificJournalRepository = scientificJournalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<GroupDto> findAll() {
        List<Group> groups = groupRepository.findAll();
        return groupRepository.findAll().stream().map(groupMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public GroupDto findById(Long id) throws ResourceNotFoundException {
        Optional<Group> groupDao = groupRepository.findById(id);
        return groupMapper.mapToDto(groupDao.orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + id + " not found.")
        ));
    }

    @Override
    public List<GroupDto> findAllByIds(List<Long> ids) {
        return groupRepository.findAllById(ids).stream().map(groupMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public GroupDto save(GroupDto groupDto) {
        return groupMapper.mapToDto(groupRepository.saveAndFlush(groupMapper.mapToDao(groupDto)));
    }

    @Override
    public GroupDto addJournal(Long scientificJournalId, Long groupId) throws ResourceNotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<Journal> journalList = group.getJournals();
        Journal newJournal = scientificJournalRepository.findById(scientificJournalId).orElseThrow(
                () -> new ResourceNotFoundException("Scientific journal with id: " + scientificJournalId + " not found.")
        );
        if (journalList.contains(newJournal)){
            return groupMapper.mapToDto(group);
        }
        journalList.add(newJournal);
        group.setJournals(journalList);
        return groupMapper.mapToDto(groupRepository.saveAndFlush(group));
    }

    @Override
    public GroupDto addUser(Long userId, Long groupId) throws ResourceNotFoundException  {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<User> userList = group.getUsers();
        User newUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + userId + " not found.")
        );
        if (userList.contains(newUser)){
            return groupMapper.mapToDto(group);
        }
        userList.add(newUser);
        group.setUsers(userList);
        return groupMapper.mapToDto(groupRepository.saveAndFlush(group));
    }

    @Override
    public void removeUser(Long userId, Long groupId) throws ResourceNotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<User> userList = group.getUsers();
        userList.remove(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + userId + " not found.")
        ));
        groupMapper.mapToDto(groupRepository.saveAndFlush(group));
    }

    @Override
    public void removeJournal(Long scientificJournalId, Long groupId) throws ResourceNotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<Journal> journalList = group.getJournals();
        journalList.remove(scientificJournalRepository.findById(scientificJournalId).orElseThrow(
                () -> new ResourceNotFoundException("Scientific journal with id: " + scientificJournalId + " not found.")
        ));
        group.setJournals(journalList);
        groupMapper.mapToDto(groupRepository.saveAndFlush(group));
    }

    @Override
    public List<ScientificJournalDto> findJournalsByGroup(Long groupId) throws ResourceNotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        return group.getJournals().stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<GroupDto> findAllByUser(Long userId) throws ResourceNotFoundException {
        List<Group> groups = groupRepository.findAll();
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + userId + " not found.")
        );
        List<Group> groupDaosByUser = new ArrayList<>();
        for(Group group : groups){
            if(group.getUsers().contains(user)){
                groupDaosByUser.add(group);
            }
        }
        return groupDaosByUser.stream().map(groupMapper::mapToDto).collect(Collectors.toList());
    }
}
