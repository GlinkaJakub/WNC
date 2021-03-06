package com.glinka.wcn;

import com.glinka.wcn.commons.InvalidPasswordException;
import com.glinka.wcn.commons.NotAuthorizedException;
import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.commons.UserAlreadyExistException;
import com.glinka.wcn.controller.dto.CategoryDto;
import com.glinka.wcn.controller.dto.GroupDto;
import com.glinka.wcn.controller.dto.GroupNameDto;
import com.glinka.wcn.controller.dto.ScientificJournalDto;
import com.glinka.wcn.controller.dto.UserDto;
import com.glinka.wcn.controller.dto.RegisterDto;
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

public class GroupDtoIntegrationTests extends BaseIntegrationTests {

    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;
    @Autowired
    ScientificJournalService journalService;
    @Autowired
    CategoryService categoryService;

    @Test
    public void testCreateGroup() throws UserAlreadyExistException, InvalidPasswordException {
        UserDto userDto = createUser();
        GroupDto groupDto1 = new GroupDto(1, "newGroup", new ArrayList<>(), userDto, new ArrayList<>());
        HttpEntity<GroupDto> group = new HttpEntity<>(groupDto1, headers);

        ResponseEntity<GroupDto> response = restTemplate.exchange(
                createURLWithPort("/api/groups"),
                HttpMethod.POST,
                group,
                GroupDto.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(groupDto1, response.getBody());
    }

    @Test
    public void testFindAllGroup() throws UserAlreadyExistException, InvalidPasswordException {
        HttpEntity<GroupDto> entity = new HttpEntity<>(null, headers);
        UserDto userDto = createUser();
        GroupNameDto groupDto1 = new GroupNameDto(1, "first group");
        GroupNameDto groupDto2 = new GroupNameDto(2, "second group");
        groupService.save(groupDto1, "email@wp.pl");
        groupService.save(groupDto2, "email@wp.pl");

        ResponseEntity<GroupDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/groups"),
                HttpMethod.GET,
                entity,
                GroupDto[].class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(groupDto1, response.getBody()[0]);
        assertEquals(groupDto2, response.getBody()[1]);
    }

    @Test
    public void testFindGroupByUser() throws UserAlreadyExistException, InvalidPasswordException {
        int userId = 1;
        HttpEntity<GroupDto> entity = new HttpEntity<>(null, headers);
        RegisterDto userDto1 = new RegisterDto(1, "email@wp.pl", "password", "password", "Jan", "Kowalski");
        RegisterDto userDto2 = new RegisterDto(2, "email2@wp.pl", "password", "password", "Jerzy", "Nowak");
        UserDto userDto11 = new UserDto(1, "email@wp.pl", "Jan", "Kowalski");
        userService.save(userDto1);
        userService.save(userDto2);
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto11);
        GroupNameDto groupDto = new GroupNameDto(3, "next group");
        groupService.save(groupDto, userDto11.getEmail());

        ResponseEntity<GroupDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/users/{userId}/groups"),
                HttpMethod.GET,
                entity,
                GroupDto[].class,
                userId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(1, response.getBody()[0].getId());
        assertEquals("Jan group", response.getBody()[0].getName());
        assertEquals(groupDto, response.getBody()[1]);
    }

    @Test
    public void testFindGroupById() throws UserAlreadyExistException, InvalidPasswordException {
        int groupId = 1;
        HttpEntity<GroupDto> entity = new HttpEntity<>(null, headers);
        RegisterDto userDto1 = new RegisterDto(1, "email@wp.pl", "password", "password", "Jan", "Kowalski");
        RegisterDto userDto2 = new RegisterDto(3, "email2@wp.pl", "password", "password", "Jerzy", "Nowak");
        userService.save(userDto1);
        userService.save(userDto2);

        ResponseEntity<GroupDto> response = restTemplate.exchange(
                createURLWithPort("/api/groups/{groupId}"),
                HttpMethod.GET,
                entity,
                GroupDto.class,
                groupId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto1.getName() + " group", response.getBody().getName());
        assertEquals(1, response.getBody().getUserDtos().size());
        assertEquals(userDto1, response.getBody().getUserDtos().get(0));

    }

    @Test
    public void testAddJournalToGroup() throws UserAlreadyExistException, InvalidPasswordException {
        int groupId = 1;
        int journalId = 1;
        HttpEntity<GroupDto> entity = new HttpEntity<>(null, headers);
        RegisterDto userDto1 = new RegisterDto(1, "email@wp.pl", "password", "password", "Jan", "Kowalski");
        userService.save(userDto1);
        CategoryDto categoryDto = new CategoryDto(1, "category");
        categoryService.save(categoryDto);
        List<CategoryDto> categories = new ArrayList<>();
        categories.add(categoryDto);
        ScientificJournalDto journal = new ScientificJournalDto(1, "title", "2345-4563", "2345-4563", "title2", "7484-4983", "7484-4983", 30, categories);
        journalService.save(journal);

        ResponseEntity<GroupDto> response = restTemplate.exchange(
                createURLWithPort("/api/groups/{groupId}/journals/{journalId}"),
                HttpMethod.GET,
                entity,
                GroupDto.class,
                groupId,
                journalId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto1, response.getBody().getUserDtos().get(0));
        assertEquals(journal, response.getBody().getJournals().get(0));
    }

    @Test
    public void testRemoveJournalFromGroup() throws ResourceNotFoundException, UserAlreadyExistException, InvalidPasswordException {
        long groupId = 1;
        long journalId = 1;
        HttpEntity<GroupDto> entity = new HttpEntity<>(null, headers);
        RegisterDto userDto1 = new RegisterDto(1, "email@wp.pl", "password", "password", "Jan", "Kowalski");
        userService.save(userDto1);
        CategoryDto categoryDto = new CategoryDto(1, "category");
        categoryService.save(categoryDto);
        List<CategoryDto> categories = new ArrayList<>();
        categories.add(categoryDto);
        ScientificJournalDto journal = new ScientificJournalDto(1, "title", "2345-4563", "2345-4563", "title2", "7484-4983", "7484-4983", 30, categories);
        journalService.save(journal);
        groupService.addJournal(journalId, groupId);

        assertEquals(1, groupService.findById(groupId).getJournals().size());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/groups/{groupId}/journals/{journalId}"),
                HttpMethod.DELETE,
                entity,
                String.class,
                groupId,
                journalId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, groupService.findById(groupId).getJournals().size());
    }

    @Test
    public void testAddUserToGroup() throws UserAlreadyExistException, InvalidPasswordException {
        long userId = 2;
        long groupId = 1;
        HttpEntity<GroupDto> entity = new HttpEntity<>(null, headers);
        RegisterDto userDto1 = new RegisterDto(1, "email@wp.pl", "password", "password", "Jan", "Kowalski");
        RegisterDto userDto2 = new RegisterDto(2, "email2@wp.pl", "password", "password", "Jerzy", "Nowak");
        userService.save(userDto1);
        userService.save(userDto2);

        ResponseEntity<GroupDto> response = restTemplate.exchange(
                createURLWithPort("/api/groups/{groupId}/users/{userId}"),
                HttpMethod.GET,
                entity,
                GroupDto.class,
                groupId,
                userId
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getUserDtos().size());
        assertEquals(userDto1, response.getBody().getUserDtos().get(0));
        assertEquals(userDto2, response.getBody().getUserDtos().get(1));
    }

    @Test
    public void testRemoveUserFromGroup() throws ResourceNotFoundException, UserAlreadyExistException, InvalidPasswordException, NotAuthorizedException {
        String newUserEmail = "email2@wp.pl";
        long groupId = 1;
        HttpEntity<GroupDto> entity = new HttpEntity<>(null, headers);
        RegisterDto userDto1 = new RegisterDto(1, "email@wp.pl", "password", "password", "Jan", "Kowalski");
        RegisterDto userDto2 = new RegisterDto(3, "email2@wp.pl", "password", "password", "Jerzy", "Nowak");
        userService.save(userDto1);
        userService.save(userDto2);
        groupService.addUser("email@wp.pl", newUserEmail, groupId);

        assertEquals(2, groupService.findById(groupId).getUserDtos().size());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/api/groups/{groupId}/users/{newUserEmail}"),
                HttpMethod.DELETE,
                entity,
                String.class,
                groupId,
                newUserEmail
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, groupService.findById(groupId).getUserDtos().size());
    }

    private UserDto createUser() throws InvalidPasswordException, UserAlreadyExistException {
        return userService.save(new RegisterDto(1, "email@wp.pl", "password", "password", "Jan", "Kowalski"));
    }
}
