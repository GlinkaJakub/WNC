package com.glinka.wcn.service2;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;

import java.util.List;


public interface ScientificJournalService {

    //   Show all journals
    List<ScientificJournal> findAll();
    List<ScientificJournal> findAllById(List<Integer> ids);
    ScientificJournal findById(Integer id) throws ResourceNotFoundException;
    //   Find journal by id/title/issn/eissn/category
    List<ScientificJournal> findAllByTitle(String word);
    List<ScientificJournal> findAllByIssn(String word);
    List<ScientificJournal> findAllByEissn(String word);
    //   Remove journal
    void delete(Integer id) throws ResourceNotFoundException;
    //   Add new journals
    ScientificJournal save(ScientificJournal scientificJournal);

    //TODO
    // find all by category
    // Find journal by group/user
//    List<ScientificJournal> findAllByUser(User user);
//    List<ScientificJournal> findAllByGroup(Group group);
//    List<ScientificJournalDao> findAllDaoById(List<Integer> ids);
}
