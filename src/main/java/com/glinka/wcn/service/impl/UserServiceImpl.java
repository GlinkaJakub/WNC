package com.glinka.wcn.service.impl;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private ConverterAdapter<User, UserDao> userDaoToDto;

    @Override
    public List<User> findAll() {
        return userDaoToDto.convertToList(userRepository.findAll());
    }

    @Override
    public User findById(int id) {
        return userDaoToDto.convert(userRepository.findById(id).orElse(null));
    }
}
