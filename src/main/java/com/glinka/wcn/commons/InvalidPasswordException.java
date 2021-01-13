package com.glinka.wcn.commons;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class InvalidPasswordException extends Exception {

    public InvalidPasswordException(String message){
        super(message);
    }

}
