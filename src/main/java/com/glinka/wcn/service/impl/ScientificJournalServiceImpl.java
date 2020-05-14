package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.repository.CategoryRepository;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScientificJournalServiceImpl implements ScientificJournalService {

    private final Mapper<ScientificJournal, ScientificJournalDao> scientificJournalMapper;
    private final ScientificJournalRepository scientificJournalRepository;
    private final CategoryRepository categoryRepository;

    public ScientificJournalServiceImpl(Mapper<ScientificJournal, ScientificJournalDao> scientificJournalMapper, ScientificJournalRepository scientificJournalRepository, CategoryRepository categoryRepository) {
        this.scientificJournalMapper = scientificJournalMapper;
        this.scientificJournalRepository = scientificJournalRepository;
        this.categoryRepository = categoryRepository;
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
    public List<ScientificJournal> findAllByCategory(Integer categoryId) throws ResourceNotFoundException {
        List<ScientificJournalDao> scientificJournalDaos = scientificJournalRepository.findAll();
        CategoryDao categoryDao = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category with id: " + categoryId + " not found.")
        );
        List<ScientificJournalDao> scientificJournalDaosByCategory = new ArrayList<>();
        for(ScientificJournalDao scientificJournalDao : scientificJournalDaos){
            if (scientificJournalDao.getCategories().contains(categoryDao)){
                scientificJournalDaosByCategory.add(scientificJournalDao);
            }
        }
        return scientificJournalDaosByCategory.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
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

