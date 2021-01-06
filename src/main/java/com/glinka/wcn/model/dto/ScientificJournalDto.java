package com.glinka.wcn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScientificJournalDto {

    @PositiveOrZero
    private long id;

    @NotBlank
    private String title1;

    @Pattern(regexp = "^[0-9]{4}-[0-9]{3}[0-9xX]$|^$")
    private String issn1;

    @Pattern(regexp = "^[0-9]{4}-[0-9]{3}[0-9xX]$|^$")
    private String eissn1;

    private String title2;

    @Pattern(regexp = "^([0-9]{4}-[0-9]{3}[0-9xX])?$|^$")
    private String issn2;

    @Pattern(regexp = "^([0-9]{4}-[0-9]{3}[0-9xX])?$|^$")
    private String eissn2;

    @PositiveOrZero
    private int points;

    private List<@Valid CategoryDto> categories;
}