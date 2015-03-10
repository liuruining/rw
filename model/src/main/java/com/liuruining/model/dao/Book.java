package com.liuruining.model.dao;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table BOOKS.
 */
public class Book implements java.io.Serializable {

    private String ID;
    private String NAME;
    private Long NUMOFLIST;
    private Long NUMOFWORD;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Book() {
    }

    public Book(String ID) {
        this.ID = ID;
    }

    public Book(String ID, String NAME, Long NUMOFLIST, Long NUMOFWORD) {
        this.ID = ID;
        this.NAME = NAME;
        this.NUMOFLIST = NUMOFLIST;
        this.NUMOFWORD = NUMOFWORD;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public Long getNUMOFLIST() {
        return NUMOFLIST;
    }

    public void setNUMOFLIST(Long NUMOFLIST) {
        this.NUMOFLIST = NUMOFLIST;
    }

    public Long getNUMOFWORD() {
        return NUMOFWORD;
    }

    public void setNUMOFWORD(Long NUMOFWORD) {
        this.NUMOFWORD = NUMOFWORD;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
