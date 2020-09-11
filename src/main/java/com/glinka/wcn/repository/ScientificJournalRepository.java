package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.ScientificJournalDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScientificJournalRepository extends JpaRepository<ScientificJournalDao, Integer> {

    List<ScientificJournalDao> findAllByTitle1ContainingOrTitle2Containing(String title1, String title2);
    List<ScientificJournalDao> findAllByIssn1ContainingOrIssn2Containing(String issn1, String issn2);
    List<ScientificJournalDao> findAllByEissn1ContainingOrEissn2Containing(String eissn1, String eissn2);

    List<ScientificJournalDao> findAllByTitle1ContainingOrTitle2ContainingOrderByEissn1(String title1, String title2);
    List<ScientificJournalDao> findAllByTitle1ContainingOrTitle2ContainingOrderByTitle1(String title1, String title2);
    List<ScientificJournalDao> findAllByTitle1ContainingOrTitle2ContainingOrderByPoints(String title1, String title2);

    List<ScientificJournalDao> findAllByIssn1ContainingOrIssn2ContainingOrderByEissn1(String issn1, String issn2);
    List<ScientificJournalDao> findAllByIssn1ContainingOrIssn2ContainingOrderByTitle1(String issn1, String issn2);
    List<ScientificJournalDao> findAllByIssn1ContainingOrIssn2ContainingOrderByPoints(String issn1, String issn2);

    List<ScientificJournalDao> findAllByEissn1ContainingOrEissn2ContainingOrderByEissn1(String eissn1, String eissn2);
    List<ScientificJournalDao> findAllByEissn1ContainingOrEissn2ContainingOrderByTitle1(String eissn1, String eissn2);
    List<ScientificJournalDao> findAllByEissn1ContainingOrEissn2ContainingOrderByPoints(String eissn1, String eissn2);

    List<ScientificJournalDao> findAllByIdOrderByEissn1(String id);
    List<ScientificJournalDao> findAllByIdOrderByTitle1(String id);
    List<ScientificJournalDao> findAllByIdOrderByPoints(String id);


}
