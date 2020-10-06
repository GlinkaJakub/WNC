package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.UserService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Mapper<User, UserDao> userMapper;
    private final UserRepository userRepository;

    private final GroupService groupService;

    public UserServiceImpl(Mapper<User, UserDao> userMapper, UserRepository userRepository, GroupService groupService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.groupService = groupService;
    }

    @Override
    public User save(User user){
        UserDao newUser = userRepository.saveAndFlush(userMapper.mapToDao(user));
        List<User> users = new ArrayList<>();
        List<ScientificJournal> scientificJournals = new ArrayList<>();
        users.add(userMapper.mapToDto(newUser));
        Group newGroup = new Group(1, newUser.getName() + " group", users, scientificJournals);
        groupService.save(newGroup);
        return userMapper.mapToDto(newUser);
    }

    @Override
    public User findById(Integer id) throws ResourceNotFoundException {
        Optional<UserDao> userDao = userRepository.findById(id);
        return userMapper.mapToDto(userDao.orElseThrow(
                () -> new ResourceNotFoundException("User with id :" + id + " not found")
        ));
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll().stream().map(userMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException{
        List<Integer> idsGroups = groupService.findAllByUser(id).stream().map(i -> i.getId()).collect(Collectors.toList());
        idsGroups.stream().forEach(idGroup -> {
            try {
                groupService.removeUser(id, idGroup);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        });
//        groupService.removeUser(id, idsGroups);
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAllById(List<Integer> ids){
        List<UserDao> userDaoList = userRepository.findAllById(ids);
        return userDaoList.stream().map(userMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<User> findAllByNameOrSurname(String name) {
        List<UserDao> userDaoList = userRepository.findAllByName(name);
        userDaoList.addAll(userRepository.findAllBySurname(name));
        return userDaoList.stream().map(userMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public User findByEmail(String email) {
        UserDao userDao = userRepository.findUserDaoByEmail(email);
        return userMapper.mapToDto(userDao);
    }

}

