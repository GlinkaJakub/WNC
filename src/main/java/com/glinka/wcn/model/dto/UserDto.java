package com.glinka.wcn.model.dto;

import com.glinka.wcn.validation.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

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

    @Size(min = 8)
    private String password;
    private String matchingPassword;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String surname;
//    private List<Group> groups;

}
