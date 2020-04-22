package com.glinka.wcn.service;

import com.glinka.wcn.model.dto.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findById(int id);

}
