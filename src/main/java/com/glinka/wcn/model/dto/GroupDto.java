package com.glinka.wcn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GroupDto {

    @PositiveOrZero
    private long id;

    @NotBlank
    private String name;

    private List<@Valid UserDto> userDtos;

    private List<@Valid ScientificJournalDto> journals;

}
