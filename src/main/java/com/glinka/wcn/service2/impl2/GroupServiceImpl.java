package com.glinka.wcn.service2.impl2;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.GroupDao;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.repository.GroupRepository;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service2.GroupService;
import com.glinka.wcn.service2.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//@Service
public class GroupServiceImpl implements GroupService {

    private final Mapper<Group, GroupDao> groupMapper;
    private final GroupRepository groupRepository;
    private final ScientificJournalRepository scientificJournalRepository;
    private final UserRepository userRepository;

    public GroupServiceImpl(Mapper<Group, GroupDao> groupMapper, GroupRepository groupRepository, ScientificJournalRepository scientificJournalRepository, UserRepository userRepository) {
        this.groupMapper = groupMapper;
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
    public Group save(Group group) {
        return groupMapper.mapToDto(groupRepository.saveAndFlush(groupMapper.mapToDao(group)));
    }

    @Override
    public Group addJournal(Integer scientificJournalId, Integer groupId) throws ResourceNotFoundException {
        GroupDao groupDao = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<ScientificJournalDao> scientificJournalDaoList = groupDao.getJournalDaos();
        scientificJournalDaoList.add(scientificJournalRepository.findById(scientificJournalId).orElseThrow(
                () -> new ResourceNotFoundException("Scientific journal with id: " + scientificJournalId + " not found.")
        ));
        groupDao.setJournalDaos(scientificJournalDaoList);
        return groupMapper.mapToDto(groupRepository.saveAndFlush(groupDao));
    }

    @Override
    public Group addUser(Integer userId, Integer groupId) throws ResourceNotFoundException  {
        GroupDao groupDao = groupRepository.findById(groupId).orElseThrow(
                () -> new ResourceNotFoundException("Group with id: " + groupId + " not found")
        );
        List<UserDao> userDaoList = groupDao.getUsers();
        userDaoList.add(userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id: " + userId + " not found.")
        ));
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
}
