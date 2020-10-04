package com.glinka.wcn.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class GroupDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    //TO DO
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UserDao> users;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<ScientificJournalDao> journalDaos;
}
