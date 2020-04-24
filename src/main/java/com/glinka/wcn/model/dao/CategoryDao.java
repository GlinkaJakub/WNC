package com.glinka.wcn.model.dao;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
//@Table(name = "category")
public class CategoryDao {

    @Id
    private int id;

    private String name;
}
