package com.glinka.wcn;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
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

public class GroupIntegrationTests extends BaseIntegrationTests {

    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;
    @Autowired
    ScientificJournalService journalService;
    @Autowired
    CategoryService categoryService;

    @Test
    public void testCreateGroup(){
        Group group1 = new Group(1, "newGroup", new ArrayList<>(), new ArrayList<>());
        HttpEntity<Group> group = new HttpEntity<>(group1, headers);

        ResponseEntity<Group> response = restTemplate.exchange(
                createURLWithPort("/api/group/saveGroup"),
                HttpMethod.POST,
                group,
                Group.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(group1, response.getBody());
    }

    @Test
    public void testFindAllGroup(){
        HttpEntity<Group> entity = new HttpEntity<>(null, headers);
        Group group1 = new Group(1, "firt group", new ArrayList<>(), new ArrayList<>());
        Group group2 = new Group(2, "second group", new ArrayList<>(), new ArrayList<>());
        groupService.save(group1);
        groupService.save(group2);

        ResponseEntity<Group[]> response = restTemplate.exchange(
                createURLWithPort("/api/group/allGroup"),
                HttpMethod.GET,
                entity,
                Group[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(group1, response.getBody()[0]);
        assertEquals(group2, response.getBody()[1]);
    }

    @Test
    public void testFindGroupByUser(){
        int userId = 1;
        HttpEntity<Group> entity = new HttpEntity<>(null, headers);
        User user1 = new User(1, "email@wp.pl", "password", "Jan", "Kowalski");
        User user2 = new User(3, "email2@wp.pl", "password", "Jerzy", "Nowak");
        userService.save(user1);
        userService.save(user2);
        List<User> users = new ArrayList<>();
        users.add(user1);
        Group group = new Group(5, "next group", users, new ArrayList<>());
        groupService.save(group);

        ResponseEntity<Group[]> response = restTemplate.exchange(
                createURLWithPort("/api/group/findGroupsByUser?userId={userId}"),
                HttpMethod.GET,
                entity,
                Group[].class,
                userId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(2, response.getBody()[0].getId());
        assertEquals("Jan group", response.getBody()[0].getName());
        assertEquals(group, response.getBody()[1]);
    }

    @Test
    public void testFindGroupById(){
        int groupId = 2;
        HttpEntity<Group> entity = new HttpEntity<>(null, headers);
        User user1 = new User(1, "email@wp.pl", "password", "Jan", "Kowalski");
        User user2 = new User(3, "email2@wp.pl", "password", "Jerzy", "Nowak");
        userService.save(user1);
        userService.save(user2);

        ResponseEntity<Group> response = restTemplate.exchange(
                createURLWithPort("/api/group/findGroupById?id={groupId}"),
                HttpMethod.GET,
                entity,
                Group.class,
                groupId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user1.getName() + " group", response.getBody().getName());
        assertEquals(1, response.getBody().getUsers().size());
        assertEquals(user1, response.getBody().getUsers().get(0));

    }

    @Test
    public void testAddJournalToGroup(){
        int groupId = 2;
        int journalId = 4;
        HttpEntity<Group> entity = new HttpEntity<>(null, headers);
        User user1 = new User(1, "email@wp.pl", "password", "Jan", "Kowalski");
        userService.save(user1);
        Category category = new Category(3, "category");
        categoryService.save(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        ScientificJournal journal = new ScientificJournal(4, "title", "issn", "eissn", "title2", "issn2", "eissn2", 30, categories);
        journalService.save(journal);

        ResponseEntity<Group> response = restTemplate.exchange(
                createURLWithPort("/api/group/addJournalToGroup?groupId={groupId}&journalId={journalId}"),
                HttpMethod.GET,
                entity,
                Group.class,
                groupId,
                journalId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(user1, response.getBody().getUsers().get(0));
        assertEquals(journal, response.getBody().getJournals().get(0));
    }

    @Test
    public void testRemoveJournalFromGroup() throws ResourceNotFoundException {
        int groupId = 2;
        int journalId = 4;
        HttpEntity<Group> entity = new HttpEntity<>(null, headers);
        User user1 = new User(1, "email@wp.pl", "password", "Jan", "Kowalski");
        userService.save(user1);
        Category category = new Category(3, "category");
        categoryService.save(category);
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        ScientificJournal journal = new ScientificJournal(4, "title", "issn", "eissn", "title2", "issn2", "eissn2", 30, categories);
        journalService.save(journal);
        groupService.addJournal(4, 2);

        assertEquals(1, groupService.findById(2).getJournals().size());

        ResponseEntity<Group> response = restTemplate.exchange(
                createURLWithPort("/api/group/removeJournalFromGroup?groupId={groupId}&journalId={journalId}"),
                HttpMethod.GET,
                entity,
                Group.class,
                groupId,
                journalId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, groupService.findById(2).getJournals().size());
    }

    @Test
    public void testAddUserToGroup(){
        int userId = 3;
        int groupId = 2;
        HttpEntity<Group> entity = new HttpEntity<>(null, headers);
        User user1 = new User(1, "email@wp.pl", "password", "Jan", "Kowalski");
        User user2 = new User(3, "email2@wp.pl", "password", "Jerzy", "Nowak");
        userService.save(user1);
        userService.save(user2);

        ResponseEntity<Group> response = restTemplate.exchange(
                createURLWithPort("/api/group/addUserToGroup?groupId={groupId}&userId={userId}"),
                HttpMethod.GET,
                entity,
                Group.class,
                groupId,
                userId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getUsers().size());
        assertEquals(user1, response.getBody().getUsers().get(0));
        assertEquals(user2, response.getBody().getUsers().get(1));
    }

    @Test
    public void testRemoveUserFromGroup() throws ResourceNotFoundException {
        int userId = 3;
        int groupId = 2;
        HttpEntity<Group> entity = new HttpEntity<>(null, headers);
        User user1 = new User(1, "email@wp.pl", "password", "Jan", "Kowalski");
        User user2 = new User(3, "email2@wp.pl", "password", "Jerzy", "Nowak");
        userService.save(user1);
        userService.save(user2);
        groupService.addUser(3, 2);

        assertEquals(2, groupService.findById(2).getUsers().size());

        ResponseEntity<Group> response = restTemplate.exchange(
                createURLWithPort("/api/group/removeUserFromGroup?groupId={groupId}&userId={userId}"),
                HttpMethod.GET,
                entity,
                Group.class,
                groupId,
                userId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, groupService.findById(2).getUsers().size());
    }


}
