package com.glinka.wcn.service.mapper;

import com.glinka.wcn.model.dao.Category;
import com.glinka.wcn.model.dao.Journal;
import com.glinka.wcn.controller.dto.CategoryDto;
import com.glinka.wcn.controller.dto.ScientificJournalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ScientificJournalMapper implements Mapper<ScientificJournalDto, Journal> {

    private final Mapper<CategoryDto, Category> categoryMapper;

    @Autowired
    public ScientificJournalMapper(Mapper<CategoryDto, Category> categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ScientificJournalDto mapToDto(Journal journal) {
        return ScientificJournalDto.builder()
                .id(journal.getJournalId())
                .title1(journal.getTitle1())
                .issn1(journal.getIssn1())
                .eissn1(journal.getEissn1())
                .title2(journal.getTitle2())
                .issn2(journal.getIssn2())
                .eissn2(journal.getEissn2())
                .points(journal.getPoints())
                .categories(journal.getCategories().stream().map(categoryMapper::mapToDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public Journal mapToDao(ScientificJournalDto scientificJournalDtoDao) {
        return Journal.builder()
                .journalId(scientificJournalDtoDao.getId())
                .title1(scientificJournalDtoDao.getTitle1())
                .issn1(scientificJournalDtoDao.getIssn1())
                .eissn1(scientificJournalDtoDao.getEissn1())
                .title2(scientificJournalDtoDao.getTitle2())
                .issn2(scientificJournalDtoDao.getIssn2())
                .eissn2(scientificJournalDtoDao.getEissn2())
                .points(scientificJournalDtoDao.getPoints())
                .categories(scientificJournalDtoDao.getCategories().stream().map(categoryMapper::mapToDao).collect(Collectors.toList()))
                .build();
    }
}
