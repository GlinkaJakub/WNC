package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.ColumnsJournal;
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
    public List<ScientificJournal> findAll(String column, String direction) {
        Sort sort = orderBy(column, direction);
        return scientificJournalRepository.findAll(sort).stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
//        if (column.equals("eissn1") || column.equals("title1") || column.equals("points")) {
//            return scientificJournalRepository.findAll(Sort.by(Sort.Direction.ASC, column)).stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
//        } else {
//            return scientificJournalRepository.findAll().stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
//        }
    }

    @Override
    public List<ScientificJournal> findAllById(List<Integer> ids, String column, String direction) {
        Sort sort = orderBy(column, direction);
        List<ScientificJournalDao> scientificJournalDaoList = scientificJournalRepository.findAllByIdIn(ids, sort);
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
    public List<ScientificJournal> findAllByTitle(String word, String column, String direction) {
        Sort sort = orderBy(column, direction);
        List<ScientificJournalDao> scientificJournalDaoList = scientificJournalRepository.findAllByTitle1ContainingOrTitle2Containing(word, word, sort);
        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournal> findAllByIssn(String word, String column, String direction) {
        Sort sort = orderBy(column, direction);
        List<ScientificJournalDao> scientificJournalDaoList = scientificJournalRepository.findAllByIssn1ContainingOrIssn2Containing(word, word, sort);
        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournal> findAllByEissn(String word, String column, String direction) {
        Sort sort = orderBy(column, direction);
        List<ScientificJournalDao> scientificJournalDaoList  = scientificJournalRepository.findAllByEissn1ContainingOrEissn2Containing(word, word, sort);
        return scientificJournalDaoList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournal> findAllByCategory(Integer categoryId, String column, String direction) throws ResourceNotFoundException {
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

    private Sort orderBy(String column, String direction){
        for(ColumnsJournal c : ColumnsJournal.values()){
            if (column.equals(c.getColumn())) {
                if (direction.equals("desc"))
                    return Sort.by(Sort.Direction.DESC, column);
                return Sort.by(Sort.Direction.ASC, column);
            }
        }
        return Sort.by(Sort.Direction.ASC, "id");
    }
}

