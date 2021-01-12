package com.glinka.wcn.service.mapper;

import com.glinka.wcn.model.dao.User;
import com.glinka.wcn.controller.dto.UserDto;
import org.springframework.stereotype.Service;


@Service
public class UserMapper implements Mapper<UserDto, User> {

    @Override
    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getUserId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .build();
    }

    @Override
    public User mapToDao(UserDto userDto) {
        return User.builder()
                .userId(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .enabled(userDto.getEnabled())
                .build();
    }
}

