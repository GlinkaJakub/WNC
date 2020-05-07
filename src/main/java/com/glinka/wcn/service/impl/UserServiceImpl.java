package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service.UserService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Mapper<User, UserDao> userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(Mapper<User, UserDao> userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user){
        return userMapper.mapToDto(userRepository.saveAndFlush(userMapper.mapToDao(user)));
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

