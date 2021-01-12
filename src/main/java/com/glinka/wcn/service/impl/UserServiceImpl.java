package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.InvalidPasswordException;
import com.glinka.wcn.commons.NotAuthorizedException;
import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.commons.UserAlreadyExistException;
import com.glinka.wcn.model.dao.Authority;
import com.glinka.wcn.model.dao.User;
import com.glinka.wcn.controller.dto.GroupDto;
import com.glinka.wcn.controller.dto.GroupNameDto;
import com.glinka.wcn.controller.dto.RegisterDto;
import com.glinka.wcn.controller.dto.UserDto;
import com.glinka.wcn.model.dao.Token;
import com.glinka.wcn.repository.AuthorityRepository;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.MailSender;
import com.glinka.wcn.service.UserService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final Mapper<UserDto, User> userMapper;
    private final Mapper<RegisterDto, User> registerUserMapper;
    private final UserRepository userRepository;
    private final GroupService groupService;
    private final PasswordEncoder encoder;
    private final AuthorityRepository authorityRepository;
    private final MailSender mailSender;

    public UserServiceImpl(Mapper<UserDto, User> userMapper, Mapper<RegisterDto, User> registerUserMapper, UserRepository userRepository, GroupService groupService, PasswordEncoder encoder, AuthorityRepository authorityRepository, MailSender mailSender) {
        this.userMapper = userMapper;
        this.registerUserMapper = registerUserMapper;
        this.userRepository = userRepository;
        this.groupService = groupService;
        this.encoder = encoder;
        this.authorityRepository = authorityRepository;
        this.mailSender = mailSender;
    }

    @Transactional
    @Override
    public UserDto save(RegisterDto registerDto) throws UserAlreadyExistException, InvalidPasswordException {
        if (emailExist(registerDto.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with email address: " + registerDto.getEmail()
            );
        }
        if (!registerDto.getPassword().equals(registerDto.getMatchingPassword())){
            throw new InvalidPasswordException(
                    "Password not matching"
            );
        }
        String hash = encoder.encode(registerDto.getPassword());
        registerDto.setPassword(hash);
        User newUser = userRepository.save(registerUserMapper.mapToDao(registerDto));
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userMapper.mapToDto(newUser));
        UserDto userDto = UserDto.builder().id(registerDto.getId()).enabled(registerDto.getEnabled()).name(registerDto.getName()).surname(registerDto.getSurname()).email(registerDto.getEmail()).build();
        GroupNameDto groupNameDto = new GroupNameDto(0, "Grupa użytkownika " + newUser.getName() + " " + newUser.getSurname());
        groupService.save(groupNameDto, newUser.getEmail());
        Authority auth = new Authority(0, userDto.getEmail(), "ROLE_USER");
        authorityRepository.save(auth);

        Token token = Token.builder()
                .id(0)
                .token(UUID.randomUUID().toString())
                .user(newUser)
                .expiryDate(calculateExpiryDate(60))
                .build();
        mailSender.save(token);
        String url = "http://localhost:8282/api/confirm?userId="+ token.getUser().getUserId() +"&token=" + token.getToken();
        mailSender.sendMail(userDto.getEmail(), "Confirm email", "<h1>Kliknij w link aby potwierdzić adres email</h1><br><a href=\"" + url +  "\" >Potwierdź email</a>");
        return userMapper.mapToDto(newUser);
    }

    @Override
    public String confirmEmail(String token, Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User with id :" + userId + " not found")
        );
        Token findToken = mailSender.getToken(userId);
        if (!findToken.getToken().equals(token)){
            return "Wrong token";
        }
        Calendar cal = Calendar.getInstance();
        if (findToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0){
            return "Token expired";
        }
        user.setEnabled(((byte) 1));
        userRepository.save(user);
        return "Email confirmed <a href=\"http://localhost:3000/login\">Login page</a>";
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
    public void delete(String owner, Long id) throws ResourceNotFoundException {
        findById(id);
        String email = findById(id).getEmail();
        List<Long> idsGroups = groupService.findAllByUser(email).stream().map(GroupDto::getId).collect(Collectors.toList());
        idsGroups.forEach(idGroup -> {
            try {
                groupService.removeUser(owner, id, idGroup);
            } catch (ResourceNotFoundException | NotAuthorizedException e) {
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

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}

