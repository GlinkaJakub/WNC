package com.glinka.wcn.service2.mapper;

import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.model.dto.ScientificJournal;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ScientificJournalMapper implements Mapper<ScientificJournal, ScientificJournalDao> {

    private final Mapper<Category, CategoryDao> categoryMapper;

    public ScientificJournalMapper(Mapper<Category, CategoryDao> categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ScientificJournal mapToDto(ScientificJournalDao scientificJournal) {
        return ScientificJournal.builder()
                .id(scientificJournal.getId())
                .title1(scientificJournal.getTitle1())
                .issn1(scientificJournal.getIssn1())
                .eissn1(scientificJournal.getEissn1())
                .title2(scientificJournal.getTitle2())
                .issn2(scientificJournal.getIssn2())
                .eissn2(scientificJournal.getEissn2())
                .points(scientificJournal.getPoints())
                .categories(scientificJournal.getCategories().stream().map(categoryMapper::mapToDto).collect(Collectors.toList()))
                .build();
    }

    @Override
    public ScientificJournalDao mapToDao(ScientificJournal scientificJournalDao) {
        return ScientificJournalDao.builder()
                .id(scientificJournalDao.getId())
                .title1(scientificJournalDao.getTitle1())
                .issn1(scientificJournalDao.getIssn1())
                .eissn1(scientificJournalDao.getEissn1())
                .title2(scientificJournalDao.getTitle2())
                .issn2(scientificJournalDao.getIssn2())
                .eissn2(scientificJournalDao.getEissn2())
                .points(scientificJournalDao.getPoints())
                .categories(scientificJournalDao.getCategories().stream().map(categoryMapper::mapToDao).collect(Collectors.toList()))
                .build();
    }
}
