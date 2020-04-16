package com.glinka.wcn.dto;

import lombok.Data;

@Data
public class User {

    private int id;
    private String email;
    private String password;
    private String name;
    private String surname;

}
