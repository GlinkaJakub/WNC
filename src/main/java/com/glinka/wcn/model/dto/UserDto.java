package com.glinka.wcn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {

    @PositiveOrZero
    private long id;

    @Email
    private String email;

    @Size(min = 8)
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;
//    private List<Group> groups;

}
