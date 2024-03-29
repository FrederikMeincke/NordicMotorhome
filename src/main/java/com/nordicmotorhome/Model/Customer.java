package com.nordicmotorhome.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    private int id;
    private String first_name;
    private String last_name;
    private String mobile;
    private String phone;
    private String email;
    private String drivers_license;
    private String dl_issue_date;
    private String dl_expire_date;
    private String street;
    private String floor;
    private int addresses_fk;
    private String zip;
    private String city;
    private String country;
    private String country_name;

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDrivers_license() {
        return drivers_license;
    }

    public void setDrivers_license(String drivers_license) {
        this.drivers_license = drivers_license;
    }

    public String getDl_issue_date() {
        return dl_issue_date;
    }

    public void setDl_issue_date(String dl_issue_date) {
        this.dl_issue_date = dl_issue_date;
    }

    public String getDl_expire_date() {
        return dl_expire_date;
    }

    public void setDl_expire_date(String dl_expire_date) {
        this.dl_expire_date = dl_expire_date;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String address) {
        this.street = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public int getAddresses_fk() {
        return addresses_fk;
    }

    public void setAddresses_fk(int addresses_fk) {
        this.addresses_fk = addresses_fk;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }
}