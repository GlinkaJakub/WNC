package com.glinka.wcn.model.dao;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
//@Table(name = "category")
public class CategoryDao {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
}
