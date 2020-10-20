package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.ScientificJournalDto;
import com.glinka.wcn.service.ScientificJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RestScientificJournalController {

    private final ScientificJournalService scientificJournalService;

    @Autowired
    public RestScientificJournalController(ScientificJournalService scientificJournalService) {
        this.scientificJournalService = scientificJournalService;
    }

    @GetMapping("/journals")
    public List<ScientificJournalDto> findAllScientificJournal(@RequestParam("column") String column, @RequestParam("direction") String direction){
        List<ScientificJournalDto> data = scientificJournalService.findAll(column, direction);
        if(data == null || data.isEmpty()){
            return Collections.emptyList();
        }
        return data;
    }

    @PostMapping("/journals")
    public ScientificJournalDto saveScientificJournal(@RequestBody ScientificJournalDto scientificJournalDto){
        return scientificJournalService.save(scientificJournalDto);
    }

    @DeleteMapping("/journals/{id}")
    public void deleteScientificJournal(@PathVariable Long id) throws ResourceNotFoundException {
        scientificJournalService.delete(id);
    }

    @GetMapping("/journals/{id}")
    public String findScientificJournal(@PathVariable Long id) throws ResourceNotFoundException {
        ScientificJournalDto data = scientificJournalService.findById(id);
        if (data == null){
            return "Not found.";
        }
        return data.toString();
    }

    @GetMapping("/journals/ids")
    public List<ScientificJournalDto> findScientificJournalsByIds(@RequestParam("ids") List<Long> ids, @RequestParam("column") String column, @RequestParam("direction") String direction){
        return scientificJournalService.findAllById(ids, column, direction);
    }

    @GetMapping("/journals/title")
    public List<ScientificJournalDto> findScientificJournalsByTitle(@RequestParam("title") String title, @RequestParam("column") String column, @RequestParam("direction") String direction){
        return scientificJournalService.findAllByTitle(title, column, direction);
    }

    @GetMapping("/journals/issn")
    public List<ScientificJournalDto> findScientificJournalsByIssn(@RequestParam("issn") String issn, @RequestParam("column") String column, @RequestParam("direction") String direction){
        return scientificJournalService.findAllByIssn(issn, column, direction);
    }

    @GetMapping("/journals/eissn")
    public List<ScientificJournalDto> findScientificJournalsByEissn(@RequestParam("eissn") String eissn, @RequestParam("column") String column, @RequestParam("direction") String direction){
        return scientificJournalService.findAllByEissn(eissn, column, direction);
    }

//    @GetMapping("/groups/{groupId}/journals")
//    public List<ScientificJournal> findJournalsByGroup(@PathVariable Integer groupId) throws ResourceNotFoundException {
//        return groupService.findJournalsByGroup(groupId);
//    }

    @GetMapping("/categories/{categoryId}/journals")
    public List<ScientificJournalDto> findAllJournalSByCategory(@PathVariable Long categoryId, @RequestParam("column") String column, @RequestParam("direction") String direction) throws Exception{
        return scientificJournalService.findAllByCategory(categoryId, column, direction);
    }

    @GetMapping("/users/{userId}/journals")
    public List<ScientificJournalDto> findAllJournalsByUser(@PathVariable Long userId, @RequestParam("column") String column, @RequestParam("direction") String direction){
        throw new IllegalArgumentException("Not implemented yet");
//        return scientificJournalService.findAllByUser(userId, column, direction);
    }

    @GetMapping("/groups/{groupId}/journals")
    public List<ScientificJournalDto> findAllJournalsByGroup(@PathVariable Long groupId, @RequestParam("column") String column, @RequestParam("direction") String direction) throws ResourceNotFoundException {
        throw new IllegalArgumentException("Not implemented yet");
//        return scientificJournalService.findAllByGroup(groupId, column, direction);
    }
}
