package com.glinka.wcn;

import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.ScientificJournalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScientificJournalIntegrationTests extends BaseIntegrationTests {

    @Autowired
    ScientificJournalService journalService;
    @Autowired
    CategoryService categoryService;

    @Test
    public void testGetAllJournals(){
        //given
        String column = "eissn1";
        String direction = "desc";
        Category category = new Category(1, "category1");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryService.save(category);
        ScientificJournal journal1 = new ScientificJournal(2, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryList);
        ScientificJournal journal2 = new ScientificJournal(3, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);

        journalService.save(journal1);
        journalService.save(journal2);

        HttpEntity<ScientificJournal> entity = new HttpEntity<>(null, headers);
        //when
        ResponseEntity<ScientificJournal[]> response = restTemplate.exchange(
                createURLWithPort("/api/journal/allScientificJournal?column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournal[].class,
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
        Category category = new Category(1, "category1");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryService.save(category);
        ScientificJournal journal = new ScientificJournal(2, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryList);

        HttpEntity<ScientificJournal> entity = new HttpEntity<>(journal, headers);

        ResponseEntity<ScientificJournal> response = restTemplate.exchange(
                createURLWithPort("/api/journal/saveScientificJournal"),
                HttpMethod.POST,
                entity,
                ScientificJournal.class
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
        Category category = new Category(1, "category1");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryService.save(category);
        ScientificJournal journal1 = new ScientificJournal(2, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryList);
        ScientificJournal journal2 = new ScientificJournal(3, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);
        ScientificJournal journal3 = new ScientificJournal(4, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<ScientificJournal> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournal> response = restTemplate.exchange(
                createURLWithPort("/api/journal/deleteScientificJournal?id={id}"),
                HttpMethod.GET,
                entity,
                ScientificJournal.class,
                id
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        int i = journalService.findAll("issn1", "asc").size();
        assertEquals(2, journalService.findAll("title1", "a").size());
    }

    // /findAllJournalsByIds
    @Test
    public void testFindAllJournalByIds(){
        String column = "issn1";
        String direction = "asc";
        List<Integer> ids = new ArrayList<>(Arrays.asList(3, 4));
        String idsAsString = idsToString(ids);

        Category category = new Category(1, "category1");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryService.save(category);
        ScientificJournal journal1 = new ScientificJournal(2, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryList);
        ScientificJournal journal2 = new ScientificJournal(3, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);
        ScientificJournal journal3 = new ScientificJournal(4, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournal[]> response = restTemplate.exchange(
                createURLWithPort("/api/journal/findAllJournalsByIds?ids={idsAsString}&column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournal[].class,
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
        String direction = "asc";
        String title = "title1";

        Category category = new Category(1, "category1");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryService.save(category);
        ScientificJournal journal1 = new ScientificJournal(2, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryList);
        ScientificJournal journal2 = new ScientificJournal(3, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);
        ScientificJournal journal3 = new ScientificJournal(4, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournal[]> response = restTemplate.exchange(
                createURLWithPort("/api/journal/findAllJournalsByTitle?title={title}&column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournal[].class,
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
        String direction = "asc";
        String issn = "issn1";

        Category category = new Category(1, "category1");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryService.save(category);
        ScientificJournal journal1 = new ScientificJournal(2, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryList);
        ScientificJournal journal2 = new ScientificJournal(3, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);
        ScientificJournal journal3 = new ScientificJournal(4, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournal[]> response = restTemplate.exchange(
                createURLWithPort("/api/journal/findAllJournalsByIssn?issn={issn}&column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournal[].class,
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
        String direction = "asc";
        String eissn = "eissn1";

        Category category = new Category(1, "category1");
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryService.save(category);
        ScientificJournal journal1 = new ScientificJournal(2, "title1", "issn1", "eissn1", "title2", "issn2", "eissn2", 30, categoryList);
        ScientificJournal journal2 = new ScientificJournal(3, "title3", "issn3", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);
        ScientificJournal journal3 = new ScientificJournal(4, "title5", "issn5", "eissn3", "title4", "issn4", "eissn4", 35, categoryList);

        journalService.save(journal1);
        journalService.save(journal2);
        journalService.save(journal3);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<ScientificJournal[]> response = restTemplate.exchange(
                createURLWithPort("/api/journal/findAllJournalsByEissn?eissn={eissn}&column={column}&direction={direction}"),
                HttpMethod.GET,
                entity,
                ScientificJournal[].class,
                eissn,
                column,
                direction
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().length);
        assertEquals(journal1, response.getBody()[0]);
    }

    // /findJournalsByGroup

    // findAllByCategory
    // findAllByUser

}
