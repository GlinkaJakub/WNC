package com.glinka.wcn;

import com.glinka.wcn.model.dto.UserDto;
import com.glinka.wcn.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDtoIntegrationTests extends BaseIntegrationTests {

    @Autowired
    UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        UserDto userDto1 = new UserDto(1, "email@wp.pl", "password", "password", "name1", "surname1");
        HttpEntity<UserDto> user = new HttpEntity<>(userDto1, headers);
        ResponseEntity<UserDto> response = restTemplate.exchange(

                createURLWithPort("/api/users"),
                HttpMethod.POST,
                user,
                UserDto.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("email@wp.pl", response.getBody().getEmail());
        assertEquals("password", response.getBody().getPassword());
        assertEquals("name1", response.getBody().getName());
        assertEquals("surname1", response.getBody().getSurname());
    }

    @Test
    public void testGetUsers() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        UserDto userDto1 = new UserDto(1, "email@wp.pl", "password", "password", "name1", "surname1");
        UserDto userDto2 = new UserDto(2, "email2@wp.pl", "password2", "password2", "name2", "surname2");
        userService.save(userDto1);
        userService.save(userDto2);
        ResponseEntity<UserDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/users"),
                HttpMethod.GET,
                entity,
                UserDto[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
    }

    @Test
    public void testFindUserById() throws Exception {
        //given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        int id = 1;
        userService.save(new UserDto(1, "email@wp.pl", "password", "password", "name1", "surname1"));
        //when
        ResponseEntity<UserDto> response = restTemplate.exchange(
                createURLWithPort("/api/users/{id}"),
                HttpMethod.GET,
                entity,
                UserDto.class,
                id);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("email@wp.pl", response.getBody().getEmail());
        assertEquals("password", response.getBody().getPassword());
        assertEquals("name1", response.getBody().getName());
        assertEquals("surname1", response.getBody().getSurname());
    }

    @Test
    public void testDeleteUser() throws Exception {
        //given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        int id = 1;
        userService.save(new UserDto(1, "email@wp.pl", "password", "password", "name1", "surname1"));
        userService.save(new UserDto(2, "email2@wp.pl", "password2", "password2", "name2", "surname2"));
        //when
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/users/{id}"),
                HttpMethod.DELETE,
                entity,
                String.class,
                id);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, userService.findAll().size());
    }

    @Test
    public void testFindUserByName() throws Exception {
        //given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String name = "name1";
        userService.save(new UserDto(1, "email@wp.pl", "password", "password", "name1", "surname1"));
        //when
        ResponseEntity<UserDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/users/names/{name}"),
                HttpMethod.GET,
                entity,
                UserDto[].class,
                name);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody()[0].getId());
        assertEquals("email@wp.pl", response.getBody()[0].getEmail());
        assertEquals("password", response.getBody()[0].getPassword());
        assertEquals("name1", response.getBody()[0].getName());
        assertEquals("surname1", response.getBody()[0].getSurname());
    }

    @Test
    public void testFindUserByEmail() throws Exception {
        //given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        String email = "email@wp.pl";
        userService.save(new UserDto(1, "email@wp.pl", "password", "password", "name1", "surname1"));
        //when
        ResponseEntity<UserDto> response = restTemplate.exchange(
                createURLWithPort("/api/users/emails/{email}"),
                HttpMethod.GET,
                entity,
                UserDto.class,
                email);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("email@wp.pl", response.getBody().getEmail());
        assertEquals("password", response.getBody().getPassword());
        assertEquals("name1", response.getBody().getName());
        assertEquals("surname1", response.getBody().getSurname());
    }

    @Test
    public void testFindUserByIds() throws Exception {
        //given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(3);
        String idsToString = idsToString(ids);
        UserDto userDto1 = new UserDto(1, "email@wp.pl", "password", "password", "name1", "surname1");
        UserDto userDto2 = new UserDto(2, "email2@wp.pl", "password2", "password2", "name2", "surname2");
        UserDto userDto3 = new UserDto(3, "email3@wp.pl", "password2", "password2", "name2", "surname2");
        UserDto userDto4 = new UserDto(4, "email4@wp.pl", "password2", "password2", "name2", "surname2");
        userService.save(userDto1);
        userService.save(userDto2);
        userService.save(userDto3);
        userService.save(userDto4);

        //when
        ResponseEntity<UserDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/users/ids?ids={idsToString}"),
                HttpMethod.GET,
                entity,
                UserDto[].class,
                idsToString);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(userDto1, response.getBody()[0]);
//        assertEquals(user2, response.getBody()[1]);
        assertEquals(1, response.getBody()[0].getId());
        assertEquals("email@wp.pl", response.getBody()[0].getEmail());
        assertEquals("password", response.getBody()[0].getPassword());
        assertEquals("name1", response.getBody()[0].getName());
        assertEquals("surname1", response.getBody()[0].getSurname());
    }

    @Test
    void contextLoads() {
        assertEquals(4, 2 + 2);
    }

}
