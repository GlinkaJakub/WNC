package com.glinka.wcn.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ScientificJournal {

    private int id;
    private String title1;
    private String issn1;
    private String eissn1;
    private String title2;
    private String issn2;
    private String eissn2;
    private int points;
    private List<Category> categories;
}