package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.InvalidPasswordException;
import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.commons.UserAlreadyExistException;
import com.glinka.wcn.model.dao.Authority;
import com.glinka.wcn.model.dao.User;
import com.glinka.wcn.model.dto.GroupDto;
import com.glinka.wcn.model.dto.ScientificJournalDto;
import com.glinka.wcn.model.dto.UserDto;
import com.glinka.wcn.repository.AuthorityRepository;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.UserService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Mapper<UserDto, User> userMapper;
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final PasswordEncoder encoder;
    private final AuthorityRepository authorityRepository;

    public UserServiceImpl(Mapper<UserDto, User> userMapper, UserRepository userRepository, GroupService groupService, PasswordEncoder encoder, AuthorityRepository authorityRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.groupService = groupService;
        this.encoder = encoder;
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    @Override
    public UserDto save(UserDto userDto) throws UserAlreadyExistException, InvalidPasswordException {
        if (emailExist(userDto.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with email address: " + userDto.getEmail()
            );
        }
        if (!userDto.getPassword().equals(userDto.getMatchingPassword())){
            throw new InvalidPasswordException(
                    "Password not matching"
            );
        }
//        String salt = BCrypt.gensalt(12);
//        String hash = BCrypt.hashpw(userDto.getPassword(), salt);
//        if (BCrypt.checkpw(userDto.getPassword(), hash)){
//            System.out.println("Zgadza sie");
//        }
        String hash = encoder.encode(userDto.getPassword());
        userDto.setPassword(hash);
        User newUser = userRepository.save(userMapper.mapToDao(userDto));
        List<UserDto> userDtos = new ArrayList<>();
        List<ScientificJournalDto> scientificJournalDtos = new ArrayList<>();
        userDtos.add(userMapper.mapToDto(newUser));
        GroupDto newGroupDto = new GroupDto(0, newUser.getName() + " group", userDtos, scientificJournalDtos);
        groupService.save(newGroupDto);
        Authority auth = new Authority(0, userDto.getEmail(), "ROLE_USER");
        authorityRepository.save(auth);
        return userMapper.mapToDto(newUser);
    }

    @Transactional
    @Override
    public UserDto changePassword(Long userId, String oldPassword, String newPassword) throws ResourceNotFoundException, InvalidPasswordException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id :" + userId + " not found"));
        if (!user.getPassword().equals(oldPassword) || newPassword.length() < 8) {
            throw new InvalidPasswordException("Wrong old password");
        }
        user.setPassword(newPassword);
        userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    @Override
    public UserDto findById(Long id) throws ResourceNotFoundException {
        Optional<User> userDao = userRepository.findById(id);
        return userMapper.mapToDto(userDao.orElseThrow(
                () -> new ResourceNotFoundException("User with id :" + id + " not found")
        ));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        findById(id);
        List<Long> idsGroups = groupService.findAllByUser(id).stream().map(GroupDto::getId).collect(Collectors.toList());
        idsGroups.forEach(idGroup -> {
            try {
                groupService.removeUser(id, idGroup);
            } catch (ResourceNotFoundException e) {
                e.printStackTrace();
            }
        });
//        groupService.removeUser(id, idsGroups);
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> findAllById(List<Long> ids) {
        List<User> userList = userRepository.findAllById(ids);
        return userList.stream().map(userMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllByNameOrSurname(String name) {
        List<User> userList = userRepository.findAllByName(name);
        userList.addAll(userRepository.findAllBySurname(name));
        return userList.stream().map(userMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return userMapper.mapToDto(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findUserByEmail(email) != null;
    }
}

