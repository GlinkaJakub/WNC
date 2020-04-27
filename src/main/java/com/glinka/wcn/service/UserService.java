package com.glinka.wcn.service;

import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.User;

import java.util.List;


public interface UserService {

    List<User> findAll();

    List<User> findAllById(List<Integer> ids);

    List<UserDao> findAllDaoById(List<Integer> ids);

    //   Find user by id/name/surname/email
    List<User> findAllByNameOrSurname(String name);

    User findById(int id);

    User findByEmail(String email);

    //   Add new User
    boolean save(User user);
}
