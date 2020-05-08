package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScientificJournalServiceImpl implements ScientificJournalService {

    private final Mapper<ScientificJournal, ScientificJournalDao> scientificJournalMapper;
    private final ScientificJournalRepository scientificJournalRepository;

    public ScientificJournalServiceImpl(Mapper<ScientificJournal, ScientificJournalDao> scientificJournalMapper, ScientificJournalRepository scientificJournalRepository) {
        this.scientificJournalMapper = scientificJournalMapper;
        this.scientificJournalRepository = scientificJournalRepository;
    }

    @Override
    public List<ScientificJournal> findAll() {
        return scientificJournalRepository.findAll().stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournal> findAllById(List<Integer> ids) {
        List<ScientificJournalDao> scientificJournalDaoList = scientificJournalRepository.findAllById(ids);
        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ScientificJournal findById(Integer id) throws ResourceNotFoundException {
        Optional<ScientificJournalDao> scientificJournalDao = scientificJournalRepository.findById(id);
        return scientificJournalMapper.mapToDto(scientificJournalDao.orElseThrow(
                () -> new ResourceNotFoundException("Scientific journal with id: " + id + " not found.")
        ));
    }

    @Override
    public List<ScientificJournal> findAllByTitle(String word) {
        List<ScientificJournalDao> scientificJournalDaoList = scientificJournalRepository.findAllByTitle1ContainingOrTitle2Containing(word, word);
        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournal> findAllByIssn(String word) {
        List<ScientificJournalDao> scientificJournalDaoList = scientificJournalRepository.findAllByIssn1ContainingOrIssn2Containing(word, word);
        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournal> findAllByEissn(String word) {
        List<ScientificJournalDao> scientificJournalDaoList = scientificJournalRepository.findAllByEissn1ContainingOrEissn2Containing(word, word);
        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) throws ResourceNotFoundException{
        scientificJournalRepository.deleteById(id);
    }

    @Override
    public ScientificJournal save(ScientificJournal scientificJournal) {
        return scientificJournalMapper.mapToDto(scientificJournalRepository.saveAndFlush(scientificJournalMapper.mapToDao(scientificJournal)));
    }
}

