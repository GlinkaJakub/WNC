package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class RestScientificJournalController {

    private final ScientificJournalService scientificJournalService;
    private final UserService userService;
    private final GroupService groupService;
    private final CategoryService categoryService;

    public RestScientificJournalController(ScientificJournalService scientificJournalService, UserService userService, GroupService groupService, CategoryService categoryService) {
        this.scientificJournalService = scientificJournalService;
        this.userService = userService;
        this.groupService = groupService;
        this.categoryService = categoryService;
    }

    @GetMapping("/allScientificJournal")
    public List<ScientificJournal> findAllScientificJournal(@RequestParam("column") String column, @RequestParam("direction") String direction){
        List<ScientificJournal> data = scientificJournalService.findAll(column, direction);
        if(data == null || data.isEmpty()){
            return Collections.emptyList();
        }
        return data;
    }

    @PostMapping("/saveScientificJournal")
    public ScientificJournal saveScientificJournal(@RequestBody ScientificJournal scientificJournal){
        return scientificJournalService.save(scientificJournal);
    }

    @GetMapping("/deleteScientificJournal")
    public void deleteScientificJournal(@RequestParam("id") Integer id) throws ResourceNotFoundException {
        scientificJournalService.delete(id);
    }

    @GetMapping("/findJournalById")
    public String findScientificJournal(@RequestParam("id") int id) throws ResourceNotFoundException {
        ScientificJournal data = scientificJournalService.findById(id);
        if (data == null){
            return "Not found.";
        }
        return data.toString();
    }

    @GetMapping("/findAllJournalsByIds")
    public List<ScientificJournal> findScientificJournalsByIds(@RequestParam("ids") List<Integer> ids, @RequestParam("column") String column, @RequestParam("direction") String direction){
        return scientificJournalService.findAllById(ids, column, direction);
    }

    @GetMapping("/findAllJournalsByTitle")
    public List<ScientificJournal> findScientificJournalsByTitle(@RequestParam("title") String title, @RequestParam("column") String column, @RequestParam("direction") String direction){
        return scientificJournalService.findAllByTitle(title, column, direction);
    }

    @GetMapping("/findAllJournalsByIssn")
    public List<ScientificJournal> findScientificJournalsByIssn(@RequestParam("issn") String issn, @RequestParam("column") String column, @RequestParam("direction") String direction){
        return scientificJournalService.findAllByIssn(issn, column, direction);
    }

    @GetMapping("/findAllJournalsByEissn")
    public List<ScientificJournal> findScientificJournalsByEissn(@RequestParam("eissn") String eissn, @RequestParam("column") String column, @RequestParam("direction") String direction){
        return scientificJournalService.findAllByEissn(eissn, column, direction);
    }

    @GetMapping("/findJournalsByGroup")
    public List<ScientificJournal> findJournalsByGroup(@RequestParam("groupId") Integer groupId) throws ResourceNotFoundException {
        return groupService.findJournalsByGroup(groupId);
    }

    @GetMapping("/findAllJournalsByCategory")
    public List<ScientificJournal> findAllJournalSByCategory(@RequestParam("categoryId") Integer categoryId, @RequestParam("column") String column, @RequestParam("direction") String direction) throws Exception{
        return scientificJournalService.findAllByCategory(categoryId, column, direction);
    }

    @GetMapping("/findAllJournalByUser")
    public List<ScientificJournal> findAllJournalsByUser(@RequestParam("userId") Integer userId, @RequestParam("column") String column, @RequestParam("direction") String direction){
        return scientificJournalService.findAllByUser(userId, column, direction);
    }

    @GetMapping("/findAllJournalsByGroup")
    public List<ScientificJournal> findAllJournalsByGroup(@RequestParam("groupId") Integer groupId, @RequestParam("column") String column, @RequestParam("direction") String direction) throws ResourceNotFoundException {
        return scientificJournalService.findAllByGroup(groupId, column, direction);
    }
}
