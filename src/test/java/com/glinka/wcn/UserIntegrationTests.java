package com.glinka.wcn;

import com.glinka.wcn.model.dto.User;
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

class UserIntegrationTests extends BaseIntegrationTests {

    @Autowired
    UserService userService;

    @Test
    public void testCreateUser() throws Exception {
        HttpEntity<User> user = new HttpEntity<>(new User(1, "email@wp.pl", "password", "name1", "surname1"), headers);
        ResponseEntity<User> response = restTemplate.exchange(
                createURLWithPort("/api/user/saveUser"),
                HttpMethod.POST,
                user,
                User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("email@wp.pl", response.getBody().getEmail());
        assertEquals("password", response.getBody().getPassword());
        assertEquals("name1", response.getBody().getName());
        assertEquals("surname1", response.getBody().getSurname());
    }

    @Test
    public void testGetUsers() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        User user1 = new User(1, "email@wp.pl", "password", "name1", "surname1");
        User user2 = new User(2, "email2@wp.pl", "password2", "name2", "surname2");
        userService.save(user1);
        userService.save(user2);
        ResponseEntity<User[]> response = restTemplate.exchange(
                createURLWithPort("/api/user/allUsers"),
                HttpMethod.GET,
                entity,
                User[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
    }

    @Test
    public void testFindUserById() throws Exception {
        //given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        int id = 1;
        userService.save(new User(1, "email@wp.pl", "password", "name1", "surname1"));
        //when
        ResponseEntity<User> response = restTemplate.exchange(
                createURLWithPort("/api/user/findUserById?id={id}"),
                HttpMethod.GET,
                entity,
                User.class,
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
        userService.save(new User(1, "email@wp.pl", "password", "name1", "surname1"));
        userService.save(new User(2, "email2@wp.pl", "password2", "name2", "surname2"));
        //when
        ResponseEntity<User> response = restTemplate.exchange(
                createURLWithPort("/api/user/deleteUser?id={id}"),
                HttpMethod.GET,
                entity,
                User.class,
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
        userService.save(new User(1, "email@wp.pl", "password", "name1", "surname1"));
        //when
        ResponseEntity<User[]> response = restTemplate.exchange(
                createURLWithPort("/api/user/findUserByName?user={name}"),
                HttpMethod.GET,
                entity,
                User[].class,
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
        userService.save(new User(1, "email@wp.pl", "password", "name1", "surname1"));
        //when
        ResponseEntity<User> response = restTemplate.exchange(
                createURLWithPort("/api/user/findUserByEmail?user={email}"),
                HttpMethod.GET,
                entity,
                User.class,
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
        User user1 = new User(1, "email@wp.pl", "password", "name1", "surname1");
        User user2 = new User(2, "email2@wp.pl", "password2", "name2", "surname2");
        User user3 = new User(3, "email3@wp.pl", "password2", "name2", "surname2");
        User user4 = new User(4, "email4@wp.pl", "password2", "name2", "surname2");
        userService.save(user1);
        userService.save(user2);
        userService.save(user3);
        userService.save(user4);

        //when
        ResponseEntity<User[]> response = restTemplate.exchange(
                createURLWithPort("/api/user/findAllUsersByIds?ids={idsToString}"),
                HttpMethod.GET,
                entity,
                User[].class,
                idsToString);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(user1, response.getBody()[0]);
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
