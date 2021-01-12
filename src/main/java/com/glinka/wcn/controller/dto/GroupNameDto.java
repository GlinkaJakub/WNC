package com.glinka.wcn.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GroupNameDto {

    @PositiveOrZero
    private long id;

    @NotBlank
    private String name;
}
