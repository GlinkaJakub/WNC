package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.GroupDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupDao, Integer> {
}
