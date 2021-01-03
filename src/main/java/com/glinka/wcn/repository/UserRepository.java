package com.glinka.wcn.repository;

import com.glinka.wcn.commons.ResourceNotFoundException;
import com.glinka.wcn.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByName(String name);
    List<User> findAllBySurname(String surname);
    User findUserByEmail(String email);

}
