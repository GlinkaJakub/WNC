package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.repository.CategoryRepository;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.data.domain.Sort;
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
    public List<ScientificJournal> findAll(String column) {
        if (column.equals("eissn1") || column.equals("title1") || column.equals("points")) {
            return scientificJournalRepository.findAll(Sort.by(Sort.Direction.ASC, column)).stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
        } else {
            return scientificJournalRepository.findAll().stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
        }
    }

    @Override
    public List<ScientificJournal> findAllById(List<Integer> ids, String column) {
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
    public List<ScientificJournal> findAllByTitle(String word, String column) {
        List<ScientificJournalDao> scientificJournalDaoList;
        if (column.equals("eissn1")) {
            scientificJournalDaoList = scientificJournalRepository.findAllByTitle1ContainingOrTitle2ContainingOrderByEissn1(word, word);
        } else if (column.equals("title1")) {
            scientificJournalDaoList = scientificJournalRepository.findAllByTitle1ContainingOrTitle2ContainingOrderByTitle1(word, word);
        } else if (column.equals("points")) {
            scientificJournalDaoList = scientificJournalRepository.findAllByTitle1ContainingOrTitle2ContainingOrderByPoints(word, word);
        } else {
            scientificJournalDaoList = scientificJournalRepository.findAllByTitle1ContainingOrTitle2Containing(word, word);
        }
        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournal> findAllByIssn(String word, String column) {
        List<ScientificJournalDao> scientificJournalDaoList;
        if (column.equals("eissn1")) {
            scientificJournalDaoList = scientificJournalRepository.findAllByIssn1ContainingOrIssn2ContainingOrderByEissn1(word, word);
        } else if (column.equals("title1")) {
            scientificJournalDaoList = scientificJournalRepository.findAllByIssn1ContainingOrIssn2ContainingOrderByTitle1(word, word);
        } else if (column.equals("points")) {
            scientificJournalDaoList = scientificJournalRepository.findAllByIssn1ContainingOrIssn2ContainingOrderByPoints(word, word);
        } else {
            scientificJournalDaoList = scientificJournalRepository.findAllByIssn1ContainingOrIssn2Containing(word, word);
        }
        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournal> findAllByEissn(String word, String column) {
        List<ScientificJournalDao> scientificJournalDaoList;
        if (column.equals("eissn1")) {
            scientificJournalDaoList = scientificJournalRepository.findAllByEissn1ContainingOrEissn2ContainingOrderByEissn1(word, word);
        } else if (column.equals("title1")) {
            scientificJournalDaoList = scientificJournalRepository.findAllByEissn1ContainingOrEissn2ContainingOrderByTitle1(word, word);
        } else if (column.equals("points")) {
            scientificJournalDaoList = scientificJournalRepository.findAllByEissn1ContainingOrEissn2ContainingOrderByPoints(word, word);
        } else {
            scientificJournalDaoList = scientificJournalRepository.findAllByEissn1ContainingOrEissn2Containing(word, word);
        }

        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournal> findAllByCategory(Integer categoryId, String column) throws ResourceNotFoundException {
        List<ScientificJournalDao> scientificJournalDaoList;
        if (column.equals("eissn1") || column.equals("title1") || column.equals("points")) {
            scientificJournalDaoList = scientificJournalRepository.findAll(Sort.by(Sort.Direction.ASC, column));
        } else {
            scientificJournalDaoList = scientificJournalRepository.findAll();
        }
        CategoryDao categoryDao = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category with id: " + categoryId + " not found.")
        );
        List<ScientificJournalDao> scientificJournalDaoByCategory = new ArrayList<>();
        for(ScientificJournalDao scientificJournalDao : scientificJournalDaoList){
            if (scientificJournalDao.getCategories().contains(categoryDao)){
                scientificJournalDaoByCategory.add(scientificJournalDao);
            }
        }
        return scientificJournalDaoByCategory.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
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

