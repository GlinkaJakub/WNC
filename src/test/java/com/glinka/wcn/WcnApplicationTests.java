package com.glinka.wcn;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) //delete date from table
class WcnApplicationTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();

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
        assertEquals("name1", response.getBody().getName());
    }

    @Test
    public void testGetUser() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<User[]> response = restTemplate.exchange(
                createURLWithPort("/api/user/allUsers"),
                HttpMethod.GET,
                entity,
                User[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().length);
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
        assertEquals("name1", response.getBody().getName());
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
        assertEquals("name1", response.getBody()[0].getName());
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
        assertEquals("name1", response.getBody().getName());
    }

    @Test
    public void testFindUserByIds() throws Exception {
        //given
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        String idsToString = ids.toString()
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();
        userService.save(new User(1, "email@wp.pl", "password", "name1", "surname1"));
        userService.save(new User(2, "email2@wp.pl", "password2", "name2", "surname2"));
        //when
        ResponseEntity<User[]> response = restTemplate.exchange(
                createURLWithPort("/api/user/findAllUsersByIds?ids={idsToString}"),
                HttpMethod.GET,
                entity,
                User[].class,
                idsToString);
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("name1", response.getBody()[0].getName());
    }

    @Test
    void contextLoads() {
        assertEquals(4, 2+2);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
