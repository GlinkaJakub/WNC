package com.glinka.wcn.controller.dto;

import com.glinka.wcn.validation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@PasswordMatches
public class UserDto {

    @PositiveOrZero
    private long id;

    @Email
    private String email;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String surname;

    @PositiveOrZero
    private byte enabled = 1;
//    private List<Group> groups;


    public UserDto(@PositiveOrZero long id, @Email String email, @NotNull @NotEmpty String name, @NotNull @NotEmpty String surname) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }
}
