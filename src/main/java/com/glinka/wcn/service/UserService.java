package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.UserDao;
import com.glinka.wcn.model.dto.User;

import java.util.List;


public interface UserService {

    List<User> findAll();

    List<User> findAllById(List<Integer> ids);
    List<UserDao> findAllDaoById(List<Integer> ids);

    //   Find user by id/name/surname/email
    List<User> findAllByNameOrSurname(String name);

//    User findById(Integer id) throws ResourceNotFoundException;
    User findById(int id);
    User findByEmail(String email);

    //   Add new User
    boolean save(User user);
//    User save(User user);

//    void delete(Integer id);
//    void delete(Integer id) throws ResourceNotFoundException;
}
