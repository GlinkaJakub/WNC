package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByName(String word);
    List<User> findAllBySurname(String word);
    User findUserByEmail(String word);

}
