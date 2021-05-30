package com.nordicmotorhome.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Country {

    @Id
    private int id;
    private String iso;
    private String name;
    private String phonecode;

    public Country(){

    }

    public Country(int id, String iso, String name, String phonecode) {
        this.id = id;
        this.iso = iso;
        this.name = name;
        this.phonecode = phonecode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonecode() {
        return phonecode;
    }

    public void setPhonecode(String phonecode) {
        this.phonecode = phonecode;
    }
}