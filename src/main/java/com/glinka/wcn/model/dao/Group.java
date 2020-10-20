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
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;

    private String name;
    //TO DO
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Journal> journals;
}
