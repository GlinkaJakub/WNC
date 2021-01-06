package com.glinka.wcn.repository;

import com.glinka.wcn.model.dao.Category;
import com.glinka.wcn.model.dao.Journal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScientificJournalRepository extends JpaRepository<Journal, Long> {

    @Query("select distinct j from Journal j join fetch j.categories where j.title1 like %:title1% or j.title2 like %:title2%")
    List<Journal> findAllByTitle1ContainingOrTitle2Containing(@Param("title1") String title1, @Param("title2") String title2, Pageable page);

    @Query("select distinct j from Journal j join fetch j.categories where j.issn1 like %?1% or j.issn2 like %?2%")
    List<Journal> findAllByIssn1ContainingOrIssn2Containing(String issn1, String issn2, Pageable page);

    @Query("select distinct j from Journal j join fetch j.categories where j.eissn1 like %?1% or j.eissn2 like %?2%")
    List<Journal> findAllByEissn1ContainingOrEissn2Containing(String eissn1, String eissn2, Pageable page);

    @Query("select distinct j from Journal j join fetch j.categories where upper(j.eissn1) like %?1% or upper(j.eissn2) like %?2% or upper(j.issn1) like %?3% or upper(j.issn2) like %?4% or upper(j.title1) like %?5% or upper(j.title2) like %?6%")
    List<Journal> findAllByEissn1ContainingOrEissn2ContainingOrIssn1ContainingOrIssn2ContainingOrTitle1ContainingOrTitle2Containing(String eissn1, String eissn2, String issn1, String issn2, String title1, String title2, Pageable page);

    @Query("select distinct j from Journal j join fetch j.categories where j.journalId in :ids")
    List<Journal> findAllByJournalIdIn(@Param("ids") List<Long> ids, Pageable page);

    @Query("select distinct j from Journal j join fetch j.categories")
    List<Journal> findAllJournals(Pageable page);

    @Query("select j from Journal j inner join j.categories c where c.categoryId in :category")
    List<Journal> findAllByCategoriesIn(@Param("category") Long category, Pageable page);
}
