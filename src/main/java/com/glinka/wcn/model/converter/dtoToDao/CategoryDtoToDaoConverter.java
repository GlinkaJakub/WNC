package com.glinka.wcn.model.converter.dtoToDao;

import com.glinka.wcn.model.converter.ConverterAdapter;
import com.glinka.wcn.model.dao.CategoryDao;
import com.glinka.wcn.model.dto.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoToDaoConverter extends ConverterAdapter<CategoryDao, Category> {

    @Override
    public CategoryDao convert(CategoryDao target, Category source) {

        if (target == null || source == null)
            return null;

        target.setId(source.getId());
        target.setName(source.getName());

        return target;
    }
}
