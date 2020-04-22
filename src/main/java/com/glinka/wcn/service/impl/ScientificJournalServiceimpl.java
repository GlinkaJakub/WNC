package com.glinka.wcn.service.impl;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.service.ScientificJournalService;

import java.util.List;

public class ScientificJournalServiceimpl implements ScientificJournalService {

    private ScientificJournalRepository scientificJournalRepository;

    private ConverterAdapter<ScientificJournal, ScientificJournalDao> scientificJournalDaoToDtoConverter;

    public ScientificJournalServiceimpl(ScientificJournalRepository scientificJournalRepository, ConverterAdapter<ScientificJournal, ScientificJournalDao> scientificJournalDaoToDtoConverter) {
        this.scientificJournalRepository = scientificJournalRepository;
        this.scientificJournalDaoToDtoConverter = scientificJournalDaoToDtoConverter;
    }

    @Override
    public List<ScientificJournal> findAll() {
        return scientificJournalDaoToDtoConverter.convertToList(scientificJournalRepository.findAll());
    }

    @Override
    public ScientificJournal findById(int id) {
        return scientificJournalDaoToDtoConverter.convert(scientificJournalRepository.findById(id).orElse(null));
    }
}
