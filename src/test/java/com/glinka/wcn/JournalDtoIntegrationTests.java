package com.glinka.wcn;

import com.glinka.wcn.model.dto.CategoryDto;
import com.glinka.wcn.model.dto.ScientificJournalDto;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JournalDtoIntegrationTests extends BaseIntegrationTests {

    @Autowired
    ScientificJournalService journalService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    GroupService groupService;
    @Autowired
    UserService userService;

    @Test
    public void testGetAllJournals(){
        //given
        String column = "eissn1";
        String direction = "DESC";
        CategoryDto categoryDto = new CategoryDto(1, "category1");
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);
        categoryService.save(categoryDto);
        ScientificJournalDto journal1 = new ScientificJournalDto(1, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryDtoList);
        ScientificJournalDto journal2 = new ScientificJournalDto(2, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);

        journalService.save(journal1);
        journalService.save(journal2);

        HttpEntity<ScientificJournalDto> entity = new HttpEntity<>(null, headers);
        //when
        ResponseEntity<ScientificJournalDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/journals?column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournalDto[].class,
                column,
                direction
        );
        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(journal2, response.getBody()[0]);
        assertEquals(journal1, response.getBody()[1]);
    }

    @Test
    public void testSaveScientificJournal(){
        CategoryDto categoryDto = new CategoryDto(1, "category1");
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);
        categoryService.save(categoryDto);
        ScientificJournalDto journal = new ScientificJournalDto(1, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryDtoList);

        HttpEntity<ScientificJournalDto> entity = new HttpEntity<>(journal, headers);

        ResponseEntity<ScientificJournalDto> response = restTemplate.exchange(
                createURLWithPort("/api/journals"),
                HttpMethod.POST,
                entity,
                ScientificJournalDto.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(journal, response.getBody());
        assertEquals("title1", response.getBody().getTitle1());
        assertEquals("issn1", response.getBody().getIssn1());
        assertEquals("eissn1", response.getBody().getEissn1());
        assertEquals("title2", response.getBody().getTitle2());
        assertEquals("issn2", response.getBody().getIssn2());
        assertEquals("eissn2", response.getBody().getEissn2());
        assertEquals(30, response.getBody().getPoints());
        assertEquals(1, response.getBody().getCategories().size());
        assertEquals("category1", response.getBody().getCategories().get(0).getName());
    }

    @Test
    public void testDeleteScientificJournal(){
        int id = 2;
        CategoryDto categoryDto = new CategoryDto(1, "category1");
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);
        categoryService.save(categoryDto);
        ScientificJournalDto journal1 = new ScientificJournalDto(1, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryDtoList);
        ScientificJournalDto journal2 = new ScientificJournalDto(2, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);
        ScientificJournalDto journal3 = new ScientificJournalDto(3, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<ScientificJournalDto> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournalDto> response = restTemplate.exchange(
                createURLWithPort("/api/journals/{id}"),
                HttpMethod.DELETE,
                entity,
                ScientificJournalDto.class,
                id
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, journalService.findAll(0, "title1", Sort.Direction.ASC).size());
    }

    // /findAllJournalsByIds
    @Test
    public void testFindAllJournalByIds(){
        String column = "issn1";
        String direction = "ASC";
        List<Integer> ids = new ArrayList<>(Arrays.asList(2, 3));
        String idsAsString = idsToString(ids);

        CategoryDto categoryDto = new CategoryDto(1, "category1");
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);
        categoryService.save(categoryDto);
        ScientificJournalDto journal1 = new ScientificJournalDto(1, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryDtoList);
        ScientificJournalDto journal2 = new ScientificJournalDto(2, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);
        ScientificJournalDto journal3 = new ScientificJournalDto(3, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournalDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/journals/ids?ids={idsAsString}&column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournalDto[].class,
                idsAsString,
                column,
                direction
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(journal2, response.getBody()[0]);
        assertEquals(journal3, response.getBody()[1]);
    }

    // /findAllJournalsByTitle
    @Test
    public void testFindAllJournalByTitle(){
        String column = "issn1";
        String direction = "ASC";
        String title = "title1";

        CategoryDto categoryDto = new CategoryDto(1, "category1");
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);
        categoryService.save(categoryDto);
        ScientificJournalDto journal1 = new ScientificJournalDto(1, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryDtoList);
        ScientificJournalDto journal2 = new ScientificJournalDto(2, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);
        ScientificJournalDto journal3 = new ScientificJournalDto(3, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournalDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/journals/title?title={title}&column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournalDto[].class,
                title,
                column,
                direction
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().length);
        assertEquals(journal1, response.getBody()[0]);
    }

    // /findAllJournalsByIssn
    @Test
    public void testFindAllJournalByIssn(){
        String column = "issn1";
        String direction = "ASC";
        String issn = "issn1";

        CategoryDto categoryDto = new CategoryDto(1, "category1");
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);
        categoryService.save(categoryDto);
        ScientificJournalDto journal1 = new ScientificJournalDto(1, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryDtoList);
        ScientificJournalDto journal2 = new ScientificJournalDto(2, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);
        ScientificJournalDto journal3 = new ScientificJournalDto(3, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournalDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/journals/issn?issn={issn}&column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournalDto[].class,
                issn,
                column,
                direction
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().length);
        assertEquals(journal1, response.getBody()[0]);
    }

    // /findAllJournalsByEissn
    @Test
    public void testFindAllJournalByEissn(){
        String column = "issn1";
        String direction = "ASC";
        String eissn = "eissn1";

        CategoryDto categoryDto = new CategoryDto(1, "category1");
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        categoryDtoList.add(categoryDto);
        categoryService.save(categoryDto);
        ScientificJournalDto journal1 = new ScientificJournalDto(1, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryDtoList);
        ScientificJournalDto journal2 = new ScientificJournalDto(2, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);
        ScientificJournalDto journal3 = new ScientificJournalDto(3, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournalDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/journals/eissn?eissn={eissn}&column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournalDto[].class,
                eissn,
                column,
                direction
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().length);
        assertEquals(journal1, response.getBody()[0]);
    }


    // findAllByCategory
    @Test
    public void testFindAllByCategory(){
        int categoryId = 2;
        String direction = "ASC";
        String column = "eissn1";

        CategoryDto categoryDto1 = new CategoryDto(1, "category1");
        CategoryDto categoryDto2 = new CategoryDto(2, "category2");
        List<CategoryDto> categoryDtoList1 = new ArrayList<>();
        List<CategoryDto> categoryDtoList2 = new ArrayList<>();
        categoryDtoList1.add(categoryDto1);
        categoryDtoList2.add(categoryDto2);
        categoryService.save(categoryDto1);
        categoryService.save(categoryDto2);

        ScientificJournalDto journal2 = new ScientificJournalDto(1, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList1);
        ScientificJournalDto journal3 = new ScientificJournalDto(2, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryDtoList2);
        ScientificJournalDto journal1 = new ScientificJournalDto(3, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryDtoList2);
        journalService.save(journal2);
        journalService.save(journal3);
        journalService.save(journal1);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournalDto[]> response = restTemplate.exchange(
                createURLWithPort("/api/categories/{categoryId}/journals?&column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournalDto[].class,
                categoryId,
                column,
                direction
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().length);
        assertEquals(journal1, response.getBody()[0]);
        assertEquals(journal3, response.getBody()[1]);
    }

    // /findJournalsByGroup
//    @Test
//    public void testFindAllByGroup() throws ResourceNotFoundException {
//        int groupId = 2;
//        String direction = "DESC";
//        String column = "issn1";
//
//        //user1
//        User user1 = new User(1, "email@wp.pl", "password", "Jan", "Kowalski");
//        //user2
//        User user2 = new User(3, "email2@wp.pl", "password2", "Jerzy", "Nowak");
//        userService.save(user1);
//        userService.save(user2);
//        Category category1 = new Category(5, "category1");
//        Category category2 = new Category(6, "category2");
//        List<Category> categoryList1 = new ArrayList<>();
//        List<Category> categoryList2 = new ArrayList<>();
//        categoryList1.add(category1);
//        categoryList2.add(category2);
//        categoryService.save(category1);
//        categoryService.save(category2);
//
//        ScientificJournal journal2 = new ScientificJournal(7, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryList1);
//        ScientificJournal journal3 = new ScientificJournal(8, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryList2);
//        ScientificJournal journal1 = new ScientificJournal(9, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryList2);
//        journalService.save(journal2);
//        journalService.save(journal3);
//        journalService.save(journal1);
//
//        groupService.addJournal(7, 2);
//        groupService.addJournal(8, 2);
//        groupService.addJournal(9, 4);
//
//        HttpEntity<String> entity = new HttpEntity<>(null, headers);
//
//        ResponseEntity<ScientificJournal[]> response = restTemplate.exchange(
//                createURLWithPort("/api/journal/findAllJournalsByGroup?groupId={groupId}&column={column}&direction={direction}"),
//                HttpMethod.GET,
//                entity,
//                ScientificJournal[].class,
//                groupId,
//                column,
//                direction
//        );
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(2, response.getBody().length);
//        assertEquals(journal3, response.getBody()[0]);
//        assertEquals(journal2, response.getBody()[1]);
//    }

    // findAllByUser

}
