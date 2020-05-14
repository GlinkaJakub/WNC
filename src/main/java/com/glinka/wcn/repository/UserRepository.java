package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDao, Integer> {

    List<UserDao> findAllByName(String word);
    List<UserDao> findAllBySurname(String word);
    UserDao findUserDaoByEmail(String word);

}
