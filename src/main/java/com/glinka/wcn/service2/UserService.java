package com.glinka.wcn.service2;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.User;

import java.util.List;


public interface UserService {

    List<User> findAll();
    List<User> findAllById(List<Integer> ids);
    List<User> findAllByNameOrSurname(String name);
    User findById(Integer id) throws ResourceNotFoundException;
    User findByEmail(String email);
    User save(User user);
    void delete(Integer id) throws ResourceNotFoundException;
}
