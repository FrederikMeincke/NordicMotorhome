package com.nordicmotorhome.Model;

import java.util.Date;

public class Customer {

    private int id;
    private String first_name;
    private String last_name;
    private int mobile;
    private int phone;
    private String email;
    private String drivers_license;
    private Date dl_issue_date;
    private Date dl_expire_date;
    private String address;
    private int zip;
    private String city;
    private String country;

    public Customer() {
    }

    public Customer(int id, String first_name, String last_name, int mobile, int phone, String email, String drivers_license,
                    Date dl_issue_date, Date dl_expire_date, String address, int zip, String city, String country) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.mobile = mobile;
        this.phone = phone;
        this.email = email;
        this.drivers_license = drivers_license;
        this.dl_issue_date = dl_issue_date;
        this.dl_expire_date = dl_expire_date;
        this.address = address;
        this.zip = zip;
        this.city = city;
        this.country = country;
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

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int mobile) {
        this.mobile = mobile;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
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

    public Date getDl_issue_date() {
        return dl_issue_date;
    }

    public void setDl_issue_date(Date dl_issue_date) {
        this.dl_issue_date = dl_issue_date;
    }

    public Date getDl_expire_date() {
        return dl_expire_date;
    }

    public void setDl_expire_date(Date dl_expire_date) {
        this.dl_expire_date = dl_expire_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
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
}
