package com.glinka.wcn.service2.mapper;

import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements Mapper<User, UserDao> {

    @Override
    public User mapToDto(UserDao userDao) {
        return User.builder()
                .id(userDao.getId())
                .name(userDao.getName())
                .surname(userDao.getSurname())
                .email(userDao.getEmail())
                .password(userDao.getPassword())
                .build();
    }

    @Override
    public UserDao mapToDao(User user) {
        return UserDao.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}

