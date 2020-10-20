package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dto.ScientificJournalDto;

import java.util.List;


public interface ScientificJournalService {

    //   Show all journals
    List<ScientificJournalDto> findAll(String column, String direction);
    List<ScientificJournalDto> findAllById(List<Long> ids, String column, String direction);
    ScientificJournalDto findById(Long id) throws ResourceNotFoundException;
    //   Find journal by id/title/issn/eissn/category
    List<ScientificJournalDto> findAllByTitle(String word, String column, String direction);
    List<ScientificJournalDto> findAllByIssn(String word, String column, String direction);
    List<ScientificJournalDto> findAllByEissn(String word, String column, String direction);
    List<ScientificJournalDto> findAllByCategory(Long categoryId, String column, String direction) throws ResourceNotFoundException;
    List<ScientificJournalDto> findAllByUser(Long userId, String column, String direction);
    List<ScientificJournalDto> findAllByGroup(Long groupId, String column, String direction) throws ResourceNotFoundException;
    //   Remove journal
    void delete(Long id) throws ResourceNotFoundException;
    //   Add new journals

    ScientificJournalDto save(ScientificJournalDto scientificJournalDto);
    //TODO
    // find all by category
    // Find journal by user
//    List<ScientificJournalDao> findAllDaoById(List<Integer> ids);
}
