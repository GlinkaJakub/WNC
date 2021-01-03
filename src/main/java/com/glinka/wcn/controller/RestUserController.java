package com.glinka.wcn.controller;

import com.glinka.wcn.commons.InvalidPasswordException;
import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.commons.UserAlreadyExistException;
import com.glinka.wcn.model.dto.LoginCredentials;
import com.glinka.wcn.model.dto.RegisterDto;
import com.glinka.wcn.model.dto.UserDto;
import com.glinka.wcn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestUserController {

    private final UserService userService;

    @Autowired
    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials credentials){

    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody @Valid RegisterDto userDto) throws UserAlreadyExistException, InvalidPasswordException {
        return new ResponseEntity<>(userService.save(userDto), HttpStatus.CREATED);
    }

    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, @PathVariable Long userId) throws ResourceNotFoundException, InvalidPasswordException {
        return new ResponseEntity<>(userService.changePassword(userId, oldPassword, newPassword), HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id , @AuthenticationPrincipal UsernamePasswordAuthenticationToken user) throws ResourceNotFoundException {
        String owner = user.getName();
        userService.delete(owner, id);
        return new ResponseEntity<>("Deleted user with id: " + id, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> findAllUsers(){
        List<UserDto> data = userService.findAll();
        if (data == null || data.isEmpty()){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> userById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/users/names/{user}")
    public ResponseEntity<List<UserDto>> findUser(@PathVariable String user){
        return new ResponseEntity<>(userService.findAllByNameOrSurname(user), HttpStatus.OK);
    }


    @GetMapping("/users/emails/{email}")
    public ResponseEntity<UserDto> findUserByEmail(@PathVariable String email){
        return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/users/ids")
    public ResponseEntity<List<UserDto>> findAllUsersByIds(@RequestParam List<Long> ids){
        return new ResponseEntity<>(userService.findAllById(ids), HttpStatus.OK);
    }

    //TODO update user
}
