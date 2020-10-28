package com.glinka.wcn.commons;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class InvalidOldPasswordException extends Exception {

    public InvalidOldPasswordException(String message){
        super(message);
    }

}
