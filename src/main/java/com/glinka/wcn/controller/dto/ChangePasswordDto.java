package com.glinka.wcn.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDto {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
