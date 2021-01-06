package com.glinka.wcn.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
//@Data
@Entity
@Setter
@Getter
@Table(name = "journals")
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long journalId;

    @NotBlank
    private String title1;

    @Pattern(regexp = "^[0-9]{4}-[0-9]{3}[0-9xX]$|^$")
    private String issn1;

    @Pattern(regexp = "^[0-9]{4}-[0-9]{3}[0-9xX]$|^$")
    private String eissn1;

    private String title2;

    @Pattern(regexp = "^([0-9]{4}-[0-9]{3}[0-9xX])?$|^$")
    private String issn2;

    @Pattern(regexp = "^([0-9]{4}-[0-9]{3}[0-9xX])?$|^$")
    private String eissn2;

    @PositiveOrZero
    private int points;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "journal_id")
    private List<@Valid Category> categories;

    @ManyToMany(mappedBy = "journals")
    private List<@Valid Group> groups;

}
