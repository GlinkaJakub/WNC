package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.GroupDao;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.repository.GroupRepository;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private final Mapper<Group, GroupDao> groupMapper;
    private final Mapper<ScientificJournal, ScientificJournalDao> scientificJournalMapper;
    private final GroupRepository groupRepository;
    private final ScientificJournalRepository scientificJournalRepository;
    private final UserRepository userRepository;

    public GroupServiceImpl(Mapper<Group, GroupDao> groupMapper, Mapper<ScientificJournal, ScientificJournalDao> scientificJournalMapper, GroupRepository groupRepository, ScientificJournalRepository scientificJournalRepository, UserRepository userRepository) {
        this.groupMapper = groupMapper;
        this.scientificJournalMapper = scientificJournalMapper;
        this.groupRepository = groupRepository;
        this.scientificJournalRepository = scientificJournalRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Group> findAll() {
        return groupRepository.findAll().stream().map(groupMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Group findById(Integer id) throws ResourceNotFoundException {
        Optional<GroupDao> groupDao = groupRepository.findById(id);
        return groupMapper.mapToDto(groupDao.orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + id + " not found.")
        ));
    }

    @Override
    public List<Group> findAllByIds(List<Integer> ids) {
        return groupRepository.findAllById(ids).stream().map(groupMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Group save(Group group) {
        return groupMapper.mapToDto(groupRepository.saveAndFlush(groupMapper.mapToDao(group)));
    }

    @Override
    public Group addJournal(Integer scientificJournalId, Integer groupId) throws ResourceNotFoundException {
        GroupDao groupDao = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<ScientificJournalDao> scientificJournalDaoList = groupDao.getJournalDaos();
        ScientificJournalDao newJournal = scientificJournalRepository.findById(scientificJournalId).orElseThrow(
                () -> new ResourceNotFoundException("Scientific journal with id: " + scientificJournalId + " not found.")
        );
        if (scientificJournalDaoList.contains(newJournal)){
            return groupMapper.mapToDto(groupDao);
        }
        scientificJournalDaoList.add(newJournal);
        groupDao.setJournalDaos(scientificJournalDaoList);
        return groupMapper.mapToDto(groupRepository.saveAndFlush(groupDao));
    }

    @Override
    public Group addUser(Integer userId, Integer groupId) throws ResourceNotFoundException  {
        GroupDao groupDao = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<UserDao> userDaoList = groupDao.getUsers();
        UserDao newUser = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + userId + " not found.")
        );
        if (userDaoList.contains(newUser)){
            return groupMapper.mapToDto(groupDao);
        }
        userDaoList.add(newUser);
        groupDao.setUsers(userDaoList);
        return groupMapper.mapToDto(groupRepository.saveAndFlush(groupDao));
    }

    @Override
    public void removeUser(Integer userId, Integer groupId) throws ResourceNotFoundException {
        GroupDao groupDao = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<UserDao> userDaoList = groupDao.getUsers();
        userDaoList.remove(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + userId + " not found.")
        ));
        groupMapper.mapToDto(groupRepository.saveAndFlush(groupDao));
    }

    @Override
    public void removeJournal(Integer scientificJournalId, Integer groupId) throws ResourceNotFoundException {
        GroupDao groupDao = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<ScientificJournalDao> scientificJournalDaoList = groupDao.getJournalDaos();
        scientificJournalDaoList.remove(scientificJournalRepository.findById(scientificJournalId).orElseThrow(
                () -> new ResourceNotFoundException("Scientific journal with id: " + scientificJournalId + " not found.")
        ));
        groupDao.setJournalDaos(scientificJournalDaoList);
        groupMapper.mapToDto(groupRepository.saveAndFlush(groupDao));
    }

    @Override
    public List<ScientificJournal> findJournalsByGroup(Integer groupId) throws ResourceNotFoundException {
        GroupDao groupDao = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        return groupDao.getJournalDaos().stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<Group> findAllByUser(Integer userId) throws ResourceNotFoundException {
        List<GroupDao> groupDaos = groupRepository.findAll();
        UserDao userDao = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + userId + " not found.")
        );
        List<GroupDao> groupDaosByUser = new ArrayList<>();
        for(GroupDao groupDao: groupDaos){
            if(groupDao.getUsers().contains(userDao)){
                groupDaosByUser.add(groupDao);
            }
        }
        return groupDaosByUser.stream().map(groupMapper::mapToDto).collect(Collectors.toList());
    }
}
