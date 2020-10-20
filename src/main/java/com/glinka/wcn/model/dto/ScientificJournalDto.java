package com.glinka.wcn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScientificJournalDto {

    private long id;
    private String title1;
    private String issn1;
    private String eissn1;
    private String title2;
    private String issn2;
    private String eissn2;
    private int points;
    private List<CategoryDto> categories;
}