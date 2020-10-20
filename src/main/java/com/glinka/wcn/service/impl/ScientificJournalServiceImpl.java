package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.ColumnsJournal;
import com.glinka.wcn.model.dao.Category;
import com.glinka.wcn.model.dao.Journal;
import com.glinka.wcn.model.dto.ScientificJournalDto;
import com.glinka.wcn.repository.CategoryRepository;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ScientificJournalServiceImpl implements ScientificJournalService {

    private final Mapper<ScientificJournalDto, Journal> scientificJournalMapper;
    private final ScientificJournalRepository scientificJournalRepository;
    private final CategoryRepository categoryRepository;
    private final GroupService groupService;
    private final UserService userService;

    @Autowired
    public ScientificJournalServiceImpl(Mapper<ScientificJournalDto, Journal> scientificJournalMapper, ScientificJournalRepository scientificJournalRepository, CategoryRepository categoryRepository, GroupService groupService, UserService userService) {
        this.scientificJournalMapper = scientificJournalMapper;
        this.scientificJournalRepository = scientificJournalRepository;
        this.categoryRepository = categoryRepository;
        this.groupService = groupService;
        this.userService = userService;
    }

    @Override
    public List<ScientificJournalDto> findAll(String column, String direction) {
        Sort sort = orderBy(column, direction);
        return scientificJournalRepository.findAll(sort).stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournalDto> findAllById(List<Long> ids, String column, String direction) {
        Sort sort = orderBy(column, direction);
        List<Journal> journalList = scientificJournalRepository.findAllByJournalIdIn(ids, sort);
        return journalList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ScientificJournalDto findById(Long id) throws ResourceNotFoundException {
        Optional<Journal> scientificJournalDao = scientificJournalRepository.findById(id);
        return scientificJournalMapper.mapToDto(scientificJournalDao.orElseThrow(
                () -> new ResourceNotFoundException("Scientific journal with id: " + id + " not found.")
        ));
    }

    @Override
    public List<ScientificJournalDto> findAllByTitle(String word, String column, String direction) {
        Sort sort = orderBy(column, direction);
        List<Journal> journalList = scientificJournalRepository.findAllByTitle1ContainingOrTitle2Containing(word, word, sort);
        return journalList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournalDto> findAllByIssn(String word, String column, String direction) {
        Sort sort = orderBy(column, direction);
        List<Journal> journalList = scientificJournalRepository.findAllByIssn1ContainingOrIssn2Containing(word, word, sort);
        return journalList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournalDto> findAllByEissn(String word, String column, String direction) {
        Sort sort = orderBy(column, direction);
        List<Journal> journalList = scientificJournalRepository.findAllByEissn1ContainingOrEissn2Containing(word, word, sort);
        return journalList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournalDto> findAllByCategory(Long categoryId, String column, String direction) throws ResourceNotFoundException {
        Sort sort = orderBy(column, direction);
        List<Journal> journalList = scientificJournalRepository.findAll(sort);
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category with id: " + categoryId + " not found.")
        );
        List<Journal> journalByCategory = new ArrayList<>();
        for(Journal journal : journalList){
            if (journal.getCategories().contains(category)){
                journalByCategory.add(journal);
            }
        }
        return journalByCategory.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournalDto> findAllByUser(Long userId, String column, String direction) {
        return null;
    }

    @Override
    public List<ScientificJournalDto>  findAllByGroup(Long groupId, String column, String direction) throws ResourceNotFoundException {
        Sort sort = orderByForGroup(column, direction);
//
//        Group group = groupService.findById(groupId);
        List<Journal> journals = scientificJournalRepository.findAllByGroup(groupId, sort);
                return journals.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException{
        scientificJournalRepository.deleteById(id);
    }

    @Override
    public ScientificJournalDto save(ScientificJournalDto scientificJournalDto) {
        return scientificJournalMapper.mapToDto(scientificJournalRepository.saveAndFlush(scientificJournalMapper.mapToDao(scientificJournalDto)));
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

    private Sort orderByForGroup(String column, String direction){
        for(ColumnsJournal c : ColumnsJournal.values()){
            if (column.equals(c.getColumn())) {
                if (direction.equals("desc"))
                    return Sort.by(Sort.Direction.DESC, "g.journals." + column);
                return Sort.by(Sort.Direction.ASC, "g.journals." + column);
            }
        }
        return Sort.by(Sort.Direction.ASC, "g.journals.id");
    }
}

