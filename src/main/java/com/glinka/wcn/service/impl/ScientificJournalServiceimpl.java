package com.glinka.wcn.service.impl;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.Group;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.model.dto.User;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.service.ScientificJournalService;

import java.util.List;

public class ScientificJournalServiceimpl implements ScientificJournalService {

    private final ScientificJournalRepository scientificJournalRepository;

    private final ConverterAdapter<ScientificJournal, ScientificJournalDao> scientificJournalDaoToDtoConverter;

    public ScientificJournalServiceimpl(ScientificJournalRepository scientificJournalRepository, ConverterAdapter<ScientificJournal, ScientificJournalDao> scientificJournalDaoToDtoConverter) {
        this.scientificJournalRepository = scientificJournalRepository;
        this.scientificJournalDaoToDtoConverter = scientificJournalDaoToDtoConverter;
    }

    @Override
    public List<ScientificJournal> findAll() {
        return scientificJournalDaoToDtoConverter.convertToList(scientificJournalRepository.findAll());
    }

    @Override
    public List<ScientificJournal> findAllById(List<Integer> ids) {
        return scientificJournalDaoToDtoConverter.convertToList(scientificJournalRepository.findAllById(ids));
    }

    @Override
    public ScientificJournal findById(int id) {
        return scientificJournalDaoToDtoConverter.convert(scientificJournalRepository.findById(id).orElse(null));
    }

    @Override
    public List<ScientificJournal> findAllBy(String word) {
        return null;
    }

    @Override
    public List<ScientificJournal> findAllByUser(User user) {
        return null;
    }

    @Override
    public List<ScientificJournal> findAllByGroup(Group group) {
        return null;
    }

    @Override
    public boolean save(ScientificJournal scientificJournal) {
        return false;
    }

    @Override
    public boolean remove(ScientificJournal scientificJournal) {
        return false;
    }
}
