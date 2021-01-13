package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<Token, Long> {
    Token findAllByUserUserId(Long userId);
}
