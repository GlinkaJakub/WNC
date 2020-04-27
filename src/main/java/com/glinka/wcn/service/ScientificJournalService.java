package com.glinka.wcn.service;

import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;

import java.util.List;


public interface ScientificJournalService {

    //   Show all journals
    List<ScientificJournal> findAll();
    List<ScientificJournal> findAllById(List<Integer> ids);
    List<ScientificJournalDao> findAllDaoById(List<Integer> ids);
    ScientificJournal findById(int id);
    //   Find journal by id/title/issn/eissn/category
    List<ScientificJournal> findAllBy(String word);
    //   Find journal by group/user
    List<ScientificJournal> findAllByUser(User user);
    List<ScientificJournal> findAllByGroup(Group group);
    //   Add new journals
    boolean save(ScientificJournal scientificJournal);
    //   Remove journal
    boolean remove(ScientificJournal scientificJournal);

}
