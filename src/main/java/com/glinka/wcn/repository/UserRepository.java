package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDao, Integer> {
}
