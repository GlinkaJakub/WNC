package com.glinka.wcn.model.dao;

import com.glinka.wcn.model.dto.User;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
//@Table(name = "group")
public class GroupDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    //TO DO
    @ManyToMany
    private List<UserDao> users;

    @ManyToMany
    private List<ScientificJournalDao> journalDaos;
}
