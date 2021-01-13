package com.glinka.wcn.service;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.controller.dto.ScientificJournalDto;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface ScientificJournalService {

    //   Show all journals
    List<ScientificJournalDto> findAll(int page, String column, Sort.Direction direction);
    List<ScientificJournalDto> findAllById(List<Long> ids, int page, String column, Sort.Direction direction);
    ScientificJournalDto findById(Long id) throws ResourceNotFoundException;
    //   Find journal by id/title/issn/eissn/category
    List<ScientificJournalDto> findAllByTitle(String word, int page, String column, Sort.Direction direction);
    List<ScientificJournalDto> findAllByIssn(String word, int page, String column, Sort.Direction direction);
    List<ScientificJournalDto> findAllByEissn(String word, int page, String column, Sort.Direction direction);
    List<ScientificJournalDto> findAllByWord(String word, int page, String column, Sort.Direction direction);
    List<ScientificJournalDto> findAllByCategory(Long categoryId, int page, String column, Sort.Direction direction) throws ResourceNotFoundException;
    List<ScientificJournalDto> findAllByUser(Long userId, int page, String column, Sort.Direction direction) throws ResourceNotFoundException;
    List<ScientificJournalDto> findAllByGroup(Long groupId, int page, String column, Sort.Direction direction) throws ResourceNotFoundException;
    //   Remove journal
    void delete(Long id) throws ResourceNotFoundException;
    //   Add new journals

    ScientificJournalDto save(ScientificJournalDto scientificJournalDto);
    //TODO
    // find all by category
    // Find journal by user
//    List<ScientificJournalDao> findAllDaoById(List<Integer> ids);
}
