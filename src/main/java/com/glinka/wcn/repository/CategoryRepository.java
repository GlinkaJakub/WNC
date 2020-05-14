package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.CategoryDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryDao, Integer> {
}
