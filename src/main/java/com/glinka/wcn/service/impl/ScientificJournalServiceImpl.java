package com.glinka.wcn.service.impl;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.Category;
import com.glinka.wcn.model.dao.Group;
import com.glinka.wcn.model.dao.Journal;
import com.glinka.wcn.model.dao.User;
import com.glinka.wcn.model.dto.GroupDto;
import com.glinka.wcn.model.dto.ScientificJournalDto;
import com.glinka.wcn.repository.CategoryRepository;
import com.glinka.wcn.repository.GroupRepository;
import com.glinka.wcn.repository.ScientificJournalRepository;
import com.glinka.wcn.repository.UserRepository;
import com.glinka.wcn.service.GroupService;
import com.glinka.wcn.service.ScientificJournalService;
import com.glinka.wcn.service.UserService;
import com.glinka.wcn.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ScientificJournalServiceImpl implements ScientificJournalService {

    public static final int SIZE = 20;
    private final Mapper<ScientificJournalDto, Journal> scientificJournalMapper;
    private final ScientificJournalRepository scientificJournalRepository;
    private final CategoryRepository categoryRepository;
    private final GroupService groupService;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public ScientificJournalServiceImpl(Mapper<ScientificJournalDto, Journal> scientificJournalMapper, ScientificJournalRepository scientificJournalRepository, CategoryRepository categoryRepository, GroupService groupService, GroupRepository groupRepository, UserRepository userRepository) {
        this.scientificJournalMapper = scientificJournalMapper;
        this.scientificJournalRepository = scientificJournalRepository;
        this.categoryRepository = categoryRepository;
        this.groupService = groupService;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ScientificJournalDto> findAll(int page, String column, Sort.Direction direction) {
        return scientificJournalRepository.findAllJournals(PageRequest.of(page, SIZE, direction, column)).stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournalDto> findAllById(List<Long> ids, int page, String column, Sort.Direction direction) {
        List<Journal> journalList = scientificJournalRepository.findAllByJournalIdIn(ids, PageRequest.of(page, SIZE, direction, column));
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
    public List<ScientificJournalDto> findAllByTitle(String word, int page, String column, Sort.Direction direction) {
        List<Journal> journalList = scientificJournalRepository.findAllByTitle1ContainingOrTitle2Containing(word, word, PageRequest.of(page, SIZE, direction, column));
        return journalList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournalDto> findAllByIssn(String word, int page, String column, Sort.Direction direction) {
        List<Journal> journalList = scientificJournalRepository.findAllByIssn1ContainingOrIssn2Containing(word, word, PageRequest.of(page, SIZE, direction, column));
        return journalList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournalDto> findAllByEissn(String word, int page, String column, Sort.Direction direction) {
        List<Journal> journalList = scientificJournalRepository.findAllByEissn1ContainingOrEissn2Containing(word, word, PageRequest.of(page, SIZE, direction, column));
        return journalList.stream().map(scientificJournalMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ScientificJournalDto> findAllByCategory(Long categoryId, int page, String column, Sort.Direction direction) throws ResourceNotFoundException {
//        Sort sort = orderBy(column, direction);
        List<Journal> journalList = scientificJournalRepository.findAllJournals(PageRequest.of(page, SIZE, direction, column));
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
    public List<ScientificJournalDto> findAllByUser(Long userId, int page, String column, Sort.Direction direction) throws ResourceNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id: " + userId + " not found"));
        List<GroupDto> groupDtos = groupService.findAllByUser(user.getUserId());
        Set<Long> journalIds = new HashSet<>();
        for (GroupDto g : groupDtos){
            journalIds.addAll(g.getJournals().stream().map(ScientificJournalDto::getId).collect(Collectors.toSet()));
        }
        return findAllById(new ArrayList<>(journalIds), page, column, direction);
    }

    @Override
    public List<ScientificJournalDto>  findAllByGroup(Long groupId, int page, String column, Sort.Direction direction) throws ResourceNotFoundException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new ResourceNotFoundException("Group with id: " + groupId + " not found"));
        List<Long> journalsIds = new ArrayList<>();
        for (Journal j : group.getJournals()){
            journalsIds.add(j.getJournalId());
        }
        return findAllById(journalsIds, page, column, direction);
    }

    @Transactional
    @Override
    public void delete(Long id) throws ResourceNotFoundException{
        Journal journals = scientificJournalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Journal with id: " + id + "    not found"));
        for (Group g : journals.getGroups()){
            long groupId = groupService.findById(g.getGroupId()).getId();
            groupService.removeJournal(id, groupId);
        }
        scientificJournalRepository.deleteById(id);
    }

    @Override
    public ScientificJournalDto save(ScientificJournalDto scientificJournalDto) {
        return scientificJournalMapper.mapToDto(scientificJournalRepository.saveAndFlush(scientificJournalMapper.mapToDao(scientificJournalDto)));
    }
}

