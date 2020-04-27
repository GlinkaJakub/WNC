package com.glinka.wcn.model.converter.dtoToDao;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.Category;
import com.glinka.wcn.model.dto.ScientificJournal;
import com.glinka.wcn.service.CategoryService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ScientificJournalDtoToDaoConverter extends ConverterAdapter<ScientificJournalDao, ScientificJournal> {

    private final CategoryService categoryService;

    public ScientificJournalDtoToDaoConverter(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public ScientificJournalDao convert(ScientificJournalDao target, ScientificJournal source) {

        if (target == null || source == null)
            return null;

        List<Integer> categoryIds = new ArrayList<>();
        for (Category category : source.getCategories()){
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
        target.setCategories(categoryService.findAllDaoById(categoryIds));

        return target;
    }
}
