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
public class GroupDto {

    private long id;
    private String name;
    private List<UserDto> userDtos;
    private List<ScientificJournalDto> journals;

}
