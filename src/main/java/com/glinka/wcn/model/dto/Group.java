package com.glinka.wcn.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class Group {

    private int id;
    private String name;
    private List<User> users;
    private List<ScientificJournal> journals;

}
