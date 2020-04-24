package com.glinka.wcn.model.converter.daoToDto;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

public class ScientificJournalDaoToDtoConverter extends ConverterAdapter<ScientificJournal, ScientificJournalDao> {

    private final CategoryService categoryService;

    public ScientificJournalDaoToDtoConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ScientificJournal convert(ScientificJournal target, ScientificJournalDao source) {

        if (target == null || source == null)
            return null;

        List<Integer> categoryIds = new ArrayList<>();
        for (CategoryDao category : source.getCategories()){
            categoryIds.add(category.getId());
        }

        target.setId(source.getId());
        target.setTitle1(source.getTitle1());
        target.setIssn1(source.getIssn1());
        target.setEissn1(source.getEissn1());
        target.setTitle2(source.getTitle2());
        target.setIssn2(source.getIssn2());
        target.setEissn2(source.getEissn2());
        target.setPoints(source.getPoints());
        target.setCategories(categoryService.findAllById(categoryIds));

        return target;
    }
}
