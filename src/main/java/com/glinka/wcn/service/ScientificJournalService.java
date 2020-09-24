package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.ScientificJournal;

import java.util.List;


public interface ScientificJournalService {

    //   Show all journals
    List<ScientificJournal> findAll(String column, String direction);
    List<ScientificJournal> findAllById(List<Integer> ids, String column, String direction);
    ScientificJournal findById(Integer id) throws ResourceNotFoundException;
    //   Find journal by id/title/issn/eissn/category
    List<ScientificJournal> findAllByTitle(String word, String column, String direction);
    List<ScientificJournal> findAllByIssn(String word, String column, String direction);
    List<ScientificJournal> findAllByEissn(String word, String column, String direction);
    List<ScientificJournal> findAllByCategory(Integer categoryId, String column, String direction) throws ResourceNotFoundException;
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
