package com.glinka.wcn.commons;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException(String message){
        super(message);
    }

}
