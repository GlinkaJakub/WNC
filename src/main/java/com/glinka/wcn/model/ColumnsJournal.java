package com.glinka.wcn.model;

public enum ColumnsJournal {
    ID("id"),
    TITLE1("title1"),
    ISSN1("issn1"),
    EISSN1("eissn1"),
    TITLE2("title2"),
    ISSN2("issn2"),
    EISSN2("eissn2"),
    POINTS("points");

    private String column;

    ColumnsJournal(String column) {
        this.column = column;
    }

    public String getColumn(){
        return column;
    }
}
