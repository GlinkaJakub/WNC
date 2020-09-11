package com.glinka.wcn.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
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

    @ManyToMany
    private List<CategoryDao> categories;

}
