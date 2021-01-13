package com.glinka.wcn.service;

import com.glinka.wcn.commons.InvalidPasswordException;
import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.commons.UserAlreadyExistException;
import com.glinka.wcn.controller.dto.ChangePasswordDto;
import com.glinka.wcn.controller.dto.RegisterDto;
import com.glinka.wcn.controller.dto.UserDto;
import com.glinka.wcn.model.dao.User;

import java.util.List;


public interface UserService {

    List<UserDto> findAll();
    List<UserDto> findAllById(List<Long> ids);
    List<UserDto> findAllByNameOrSurname(String name);
    UserDto findById(Long id) throws ResourceNotFoundException;
    UserDto findByEmail(String email);
    UserDto save(RegisterDto userDto) throws UserAlreadyExistException, InvalidPasswordException;
    void delete(String owner, Long id) throws ResourceNotFoundException;
    String confirmEmail(String token, Long userId) throws ResourceNotFoundException;

    UserDto changePassword(String user, ChangePasswordDto changePasswordDto) throws ResourceNotFoundException, InvalidPasswordException;
}
