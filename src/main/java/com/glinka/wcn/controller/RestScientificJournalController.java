package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.ScientificJournalDto;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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
    public ResponseEntity<List<ScientificJournalDto>> findAllScientificJournal(@RequestParam(required = false, defaultValue = "0") int page,
                                                               @RequestParam(required = false, defaultValue = "journalId") String column,
                                                               @RequestParam(required = false, defaultValue = "ASC") Sort.Direction  direction){
        int pageNumber = page >= 0 ? page : 0;
        List<ScientificJournalDto> data = scientificJournalService.findAll(pageNumber, column, direction);
        //TODO
        if (data == null || data.isEmpty()){
            return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/journals")
    public ResponseEntity<ScientificJournalDto> saveScientificJournal(@RequestBody @Valid ScientificJournalDto scientificJournalDto){
        return new ResponseEntity<>(scientificJournalService.save(scientificJournalDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/journals/{id}")
    public ResponseEntity<String> deleteScientificJournal(@PathVariable Long id) throws ResourceNotFoundException {
        scientificJournalService.delete(id);
        return new ResponseEntity<>("Deleted journal with id: " + id, HttpStatus.OK);
    }

    @GetMapping("/journals/{id}")
    public ResponseEntity<ScientificJournalDto> findScientificJournal(@PathVariable Long id) throws ResourceNotFoundException {
        ScientificJournalDto data = scientificJournalService.findById(id);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/journals/ids")
    public ResponseEntity<List<ScientificJournalDto>> findScientificJournalsByIds(@RequestParam("ids") List<Long> ids,
                                                                  @RequestParam(required = false, defaultValue = "0") int page,
                                                                  @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                  @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction){
        int pageNumber = page >= 0 ? page : 0;
        return new ResponseEntity<>(scientificJournalService.findAllById(ids, pageNumber, column, direction), HttpStatus.OK);
    }

    @GetMapping("/journals/title")
    public ResponseEntity<List<ScientificJournalDto>> findScientificJournalsByTitle(@RequestParam("title") String title,
                                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                                    @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                    @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction){
        return new ResponseEntity<>(scientificJournalService.findAllByTitle(title, page, column, direction), HttpStatus.OK);
    }

    @GetMapping("/journals/issn")
    public ResponseEntity<List<ScientificJournalDto>> findScientificJournalsByIssn(@RequestParam("issn") String issn,
                                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                                   @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                   @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction){
        return new ResponseEntity<>(scientificJournalService.findAllByIssn(issn, page, column, direction), HttpStatus.OK);
    }

    @GetMapping("/journals/eissn")
    public ResponseEntity<List<ScientificJournalDto>> findScientificJournalsByEissn(@RequestParam("eissn") String eissn,
                                                                    @RequestParam(required = false, defaultValue = "0") int page,
                                                                    @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                    @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction){
        return new ResponseEntity<>(scientificJournalService.findAllByEissn(eissn, page, column, direction), HttpStatus.OK);
    }

//    @GetMapping("/groups/{groupId}/journals")
//    public List<ScientificJournalDto> findJournalsByGroup(@PathVariable Long groupId) throws ResourceNotFoundException {
//        return groupService.findJournalsByGroup(groupId);
//    }

    @GetMapping("/categories/{categoryId}/journals")
    public ResponseEntity<List<ScientificJournalDto>> findAllJournalSByCategory(@PathVariable Long categoryId,
                                                                @RequestParam(required = false, defaultValue = "0") int page,
                                                                @RequestParam(required = false, defaultValue = "journalId") String column,
                                                                @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction) throws Exception{
        return new ResponseEntity<>(scientificJournalService.findAllByCategory(categoryId, page, column, direction), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/journals")
    public ResponseEntity<List<ScientificJournalDto>> findAllJournalsByUser(@PathVariable Long userId,
                                                            @RequestParam(required = false, defaultValue = "0") int page,
                                                            @RequestParam(required = false, defaultValue = "journalId") String column,
                                                            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction) throws ResourceNotFoundException {
        int pageNumber = page >= 0 ? page : 0;
//        throw new IllegalArgumentException("Not implemented yet");
        return new ResponseEntity<>(scientificJournalService.findAllByUser(userId, pageNumber, column, direction), HttpStatus.OK);
    }

    @GetMapping("/groups/{groupId}/journals")
    public ResponseEntity<List<ScientificJournalDto>> findAllJournalsByGroup(@PathVariable Long groupId,
                                                             @RequestParam(required = false, defaultValue = "0") int page,
                                                             @RequestParam(required = false, defaultValue = "journalId") String column,
                                                             @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction) throws ResourceNotFoundException {
//        throw new IllegalArgumentException("Not implemented yet");
        int pageNumber = page >= 0 ? page : 0;
        return new ResponseEntity<>(scientificJournalService.findAllByGroup(groupId, pageNumber, column, direction), HttpStatus.OK);
    }

    //TODO update journal
}
