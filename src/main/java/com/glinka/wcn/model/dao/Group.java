package com.glinka.wcn.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.action.internal.OrphanRemovalAction;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Data
@Setter
@Getter
@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long groupId;

    @NotBlank
    private String name;
    //TO DO
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<@Valid User> users;

    @ManyToOne
    private User owner;

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<@Valid Journal> journals;
}
