package com.glinka.wcn.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {

    private int id;
    private String email;
    private String password;
    private String name;
    private String surname;
//    private List<Group> groups;

}
