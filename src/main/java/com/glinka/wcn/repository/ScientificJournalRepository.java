package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.ScientificJournalDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScientificJournalRepository extends JpaRepository<ScientificJournalDao, Integer> {

    List<ScientificJournalDao> findAllByTitle1ContainingOrTitle2Containing(String title1, String title2);
    List<ScientificJournalDao> findAllByIssn1ContainingOrIssn2Containing(String issn1, String issn2);
    List<ScientificJournalDao> findAllByEissn1ContainingOrEissn2Containing(String eissn1, String eissn2);
}
