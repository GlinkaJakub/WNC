package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.ScientificJournal;

import java.util.List;


public interface ScientificJournalService {

    //   Show all journals
    List<ScientificJournal> findAll(String column);
    List<ScientificJournal> findAllById(List<Integer> ids, String column);
    ScientificJournal findById(Integer id) throws ResourceNotFoundException;
    //   Find journal by id/title/issn/eissn/category
    List<ScientificJournal> findAllByTitle(String word, String column);
    List<ScientificJournal> findAllByIssn(String word, String column);
    List<ScientificJournal> findAllByEissn(String word, String column);
    List<ScientificJournal> findAllByCategory(Integer categoryId, String column) throws ResourceNotFoundException;
    //   Remove journal
    void delete(Integer id) throws ResourceNotFoundException;
    //   Add new journals
    ScientificJournal save(ScientificJournal scientificJournal);

    //TODO
    // find all by category
    // Find journal by user
//    List<ScientificJournal> findAllByUser(User user);
//    List<ScientificJournalDao> findAllDaoById(List<Integer> ids);
}
