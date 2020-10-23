package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.ScientificJournalDto;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    private final GroupService groupService;

    @Autowired
    public RestScientificJournalController(ScientificJournalService scientificJournalService, GroupService groupService) {
        this.scientificJournalService = scientificJournalService;
        this.groupService = groupService;
    }

    @GetMapping("/journals")
    public List<ScientificJournalDto> findAllScientificJournal(@RequestParam(required = false, defaultValue = "0") int page,
                                                               @RequestParam(required = false, defaultValue = "journalId") String column,
                                                               @RequestParam(required = false, defaultValue = "ASC") Sort.Direction  direction){
        int pageNumber = page >= 0 ? page : 0;
        List<ScientificJournalDto> data = scientificJournalService.findAll(pageNumber, column, direction);
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
    public List<ScientificJournalDto> findScientificJournalsByIds(@RequestParam("ids") List<Long> ids,
                                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                                  @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                  @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction){
        int pageNumber = page >= 0 ? page : 0;
        return scientificJournalService.findAllById(ids, pageNumber, column, direction);
    }

    @GetMapping("/journals/title")
    public List<ScientificJournalDto> findScientificJournalsByTitle(@RequestParam("title") String title,
                                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                                    @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                    @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction){
        return scientificJournalService.findAllByTitle(title, page, column, direction);
    }

    @GetMapping("/journals/issn")
    public List<ScientificJournalDto> findScientificJournalsByIssn(@RequestParam("issn") String issn,
                                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                                   @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                   @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction){
        return scientificJournalService.findAllByIssn(issn, page, column, direction);
    }

    @GetMapping("/journals/eissn")
    public List<ScientificJournalDto> findScientificJournalsByEissn(@RequestParam("eissn") String eissn,
                                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                                    @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                    @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction){
        return scientificJournalService.findAllByEissn(eissn, page, column, direction);
    }

//    @GetMapping("/groups/{groupId}/journals")
//    public List<ScientificJournalDto> findJournalsByGroup(@PathVariable Long groupId) throws ResourceNotFoundException {
//        return groupService.findJournalsByGroup(groupId);
//    }

    @GetMapping("/categories/{categoryId}/journals")
    public List<ScientificJournalDto> findAllJournalSByCategory(@PathVariable Long categoryId,
                                                                @RequestParam(required = false, defaultValue = "0") int page,
                                                                @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction) throws Exception{
        return scientificJournalService.findAllByCategory(categoryId, page, column, direction);
    }

    @GetMapping("/users/{userId}/journals")
    public List<ScientificJournalDto> findAllJournalsByUser(@PathVariable Long userId,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "journalId") String column,
                                                            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction) throws ResourceNotFoundException {
        int pageNumber = page >= 0 ? page : 0;
//        throw new IllegalArgumentException("Not implemented yet");
        return scientificJournalService.findAllByUser(userId, pageNumber, column, direction);
    }

    @GetMapping("/groups/{groupId}/journals")
    public List<ScientificJournalDto> findAllJournalsByGroup(@PathVariable Long groupId,
                                                             @RequestParam(required = false, defaultValue = "0") int page,
                                                             @RequestParam(required = false, defaultValue = "journalId") String column,
                                                             @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction) throws ResourceNotFoundException {
//        throw new IllegalArgumentException("Not implemented yet");
        int pageNumber = page >= 0 ? page : 0;
        return scientificJournalService.findAllByGroup(groupId, pageNumber, column, direction);
    }

    //TODO update journal
}
