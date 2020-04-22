package com.glinka.wcn.service;

import com.glinka.wcn.model.dto.ScientificJournal;

import java.util.List;

public interface ScientificJournalService {

    List<ScientificJournal> findAll();
    ScientificJournal findById(int id);

}
