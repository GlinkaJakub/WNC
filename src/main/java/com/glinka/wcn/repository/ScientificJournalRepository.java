package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.Journal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScientificJournalRepository extends JpaRepository<Journal, Long> {

    List<Journal> findAllByTitle1ContainingOrTitle2Containing(String title1, String title2, Pageable page);
    List<Journal> findAllByIssn1ContainingOrIssn2Containing(String issn1, String issn2, Pageable page);
    List<Journal> findAllByEissn1ContainingOrEissn2Containing(String eissn1, String eissn2, Pageable page);

    List<Journal> findAllByJournalIdIn(List<Long> ids, Pageable page);

    @Query("select j from Journal j")
    List<Journal> findAllJournals(Pageable page);

    @Query("select g.journals from Group g where g.groupId = ?1")
    List<Journal> findAllByGroup(Long groupId, Pageable page);

}
