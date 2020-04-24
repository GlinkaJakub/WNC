package com.glinka.wcn.service.impl;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ConverterAdapter<User, UserDao> userDaoToDto;

    public UserServiceImpl(UserRepository userRepository, ConverterAdapter<User, UserDao> userDaoToDto) {
        this.userRepository = userRepository;
        this.userDaoToDto = userDaoToDto;
    }

    @Override
    public List<User> findAll() {
        return userDaoToDto.convertToList(userRepository.findAll());
    }

    @Override
    public User findById(int id) {
        return userDaoToDto.convert(userRepository.findById(id).orElse(null));
    }

    @Override
    public List<User> findAllById(List<Integer> ids) {
        return userDaoToDto.convertToList(userRepository.findAllById(ids));
    }

    @Override
    public List<User> findAllByNameOrSurname(String name) {
        List<UserDao> userDaosList = userRepository.findAllByName(name);
        UserDao userDao = userRepository.findUserDaoByEmail(name);

        for (UserDao u : userRepository.findAllBySurname(name)){
            if (!userDaosList.contains(u))
                userDaosList.add(u);
        }
        if (!userDaosList.contains(userRepository.findUserDaoByEmail(name)))
            userDaosList.add(userDao);

        return userDaoToDto.convertToList(userDaosList);
    }

    @Override
    public User findByEmail(String email) {
        return userDaoToDto.convert(userRepository.findUserDaoByEmail(email));
    }

    @Override
    public boolean save(User user) {
        return false;
    }
}
