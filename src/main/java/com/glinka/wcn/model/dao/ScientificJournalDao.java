package com.glinka.wcn.model.dao;

import com.glinka.wcn.model.dto.Category;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
//@Table(name = "scientific_journal")
public class ScientificJournalDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title1;
    private String issn1;
    private String eissn1;
    private String title2;
    private String issn2;
    private String eissn2;
    private int points;

    @OneToMany
    private List<CategoryDao> categories;

}
