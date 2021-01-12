package com.glinka.wcn.validation;

import com.glinka.wcn.controller.dto.RegisterDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,  Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        RegisterDto user = (RegisterDto) o;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
