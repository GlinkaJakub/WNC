package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.ScientificJournalDao;
import com.glinka.wcn.model.dto.ScientificJournal;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScientificJournalRepository extends JpaRepository<ScientificJournalDao, Integer> {

    List<ScientificJournalDao> findAllByTitle1ContainingOrTitle2Containing(String title1, String title2, Sort sort);
    List<ScientificJournalDao> findAllByIssn1ContainingOrIssn2Containing(String issn1, String issn2, Sort sort);
    List<ScientificJournalDao> findAllByEissn1ContainingOrEissn2Containing(String eissn1, String eissn2, Sort sort);

    List<ScientificJournalDao> findAllByIdIn(List<Integer> ids, Sort sort);

    @Query("select g.journalDaos from GroupDao g where g.id = ?1")
    List<ScientificJournalDao> findAllByGroup(Integer groupId, Sort sort);

}
