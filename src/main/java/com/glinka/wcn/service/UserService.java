package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.UserDto;

import java.util.List;


public interface UserService {

    List<UserDto> findAll();
    List<UserDto> findAllById(List<Long> ids);
    List<UserDto> findAllByNameOrSurname(String name);
    UserDto findById(Long id) throws ResourceNotFoundException;
    UserDto findByEmail(String email);
    UserDto save(UserDto userDto);
    void delete(Long id) throws ResourceNotFoundException;
}
