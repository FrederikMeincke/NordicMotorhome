package com.nordicmotorhome.Model;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Zip {
    @Id
    private int id;
    private String zip;
    private String city;
    private int countries_fk;

    public Zip(){

    }

    public Zip(int id, String zip, String city, int countries_fk){
        this.id = id;
        this.zip = zip;
        this.city = city;
        this.countries_fk = countries_fk;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getZip(){
        return zip;
    }

    public void setZip(String zip){
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountries_fk() {
        return countries_fk;
    }

    public void setCountries_fk(int countries_fk) {
        this.countries_fk = countries_fk;
    }

}