package com.glinka.wcn.service.mapper;

import com.glinka.wcn.model.dao.User;
import com.glinka.wcn.model.dto.RegisterDto;
import com.glinka.wcn.model.dto.UserDto;
import org.springframework.stereotype.Service;


@Service
public class RegisterUserMapper implements Mapper<RegisterDto, User> {

    @Override
    public RegisterDto mapToDto(User user) {
        return RegisterDto.builder()
                .id(user.getUserId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .matchingPassword(user.getPassword())
                .enabled(user.getEnabled())
                .build();
    }

    @Override
    public User mapToDao(RegisterDto userDto) {
        return User.builder()
                .userId(userDto.getId())
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .enabled(userDto.getEnabled())
                .build();
    }
}

