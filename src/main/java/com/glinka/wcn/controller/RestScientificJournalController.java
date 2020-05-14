package com.glinka.wcn.controller;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.service.CategoryService;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import org.springframework.web.bind.annotation.*;

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
    public String findAllScientificJournal(){
        List<ScientificJournal> data = scientificJournalService.findAll();
        if(data == null || data.isEmpty()){
            return "Not found data";
        }
        return data.toString();
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
    public List<ScientificJournal> findScientificJournalsByIds(@RequestParam("ids") List<Integer> ids){
        return scientificJournalService.findAllById(ids);
    }

    @GetMapping("/findAllJournalsByTitle")
    public List<ScientificJournal> findScientificJournalsByTitle(@RequestParam("title") String title){
        return scientificJournalService.findAllByTitle(title);
    }

    @GetMapping("/findAllJournalsByIssn")
    public List<ScientificJournal> findScientificJournalsByIssn(@RequestParam("issn") String issn){
        return scientificJournalService.findAllByIssn(issn);
    }

    @GetMapping("/findAllJournalsByEissn")
    public List<ScientificJournal> findScientificJournalsByEissn(@RequestParam("eissn") String eissn){
        return scientificJournalService.findAllByEissn(eissn);
    }

    @GetMapping("/findJournalsByGroup")
    public List<ScientificJournal> findJournalsByGroup(@RequestParam("groupId") Integer groupId) throws ResourceNotFoundException {
        return groupService.findJournalsByGroup(groupId);
    }

}
