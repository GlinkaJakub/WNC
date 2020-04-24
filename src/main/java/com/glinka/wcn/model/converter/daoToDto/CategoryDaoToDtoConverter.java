package com.glinka.wcn.model.converter.daoToDto;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dto.Category;

public class CategoryDaoToDtoConverter extends ConverterAdapter<Category, CategoryDao> {

    @Override
    public Category convert(Category target, CategoryDao source) {

        if (target == null || source == null)
            return null;

        target.setId(source.getId());
        target.setName(source.getName());

        return target;
    }
}
