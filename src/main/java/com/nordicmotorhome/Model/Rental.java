package com.nordicmotorhome.Model;

import java.util.Date;
import java.util.List;

public class Rental {

    private int id;
    private Customer customer;
    private Motorhome motorhome;
    private Season season;
    private List<Accessory> accessoryList;
    private Date start_date;
    private Date end_date;
    private String pick_up_location;
    private String drop_off_location;
    private double total_price;
    private int customers_fk;
    private int motorhomes_fk;
    private int seasons_fk;

    public Rental() {
    }

    public Rental(int id, Customer customer, Motorhome motorhome, Season season, List<Accessory> accessoryList,
                  Date start_date, Date end_date, String pick_up_location, String drop_off_location,
                  double total_price) {
        this.id = id;
        this.customer = customer;
        this.motorhome = motorhome;
        this.season = season;
        this.accessoryList = accessoryList;
        this.start_date = start_date;
        this.end_date = end_date;
        this.pick_up_location = pick_up_location;
        this.drop_off_location = drop_off_location;
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Motorhome getMotorhome() {
        return motorhome;
    }

    public void setMotorhome(Motorhome motorhome) {
        this.motorhome = motorhome;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public List<Accessory> getAccessoryList() {
        return accessoryList;
    }

    public void setAccessoryList(List<Accessory> accessoryList) {
        this.accessoryList = accessoryList;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getPick_up_location() {
        return pick_up_location;
    }

    public void setPick_up_location(String pick_up_location) {
        this.pick_up_location = pick_up_location;
    }

    public String getDrop_off_location() {
        return drop_off_location;
    }

    public void setDrop_off_location(String drop_off_location) {
        this.drop_off_location = drop_off_location;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public int getCustomers_fk() {
        return customers_fk;
    }

    public void setCustomers_fk(int customers_fk) {
        this.customers_fk = customers_fk;
    }

    public int getMotorhomes_fk() {
        return motorhomes_fk;
    }

    public void setMotorhomes_fk(int motorhomes_fk) {
        this.motorhomes_fk = motorhomes_fk;
    }

    public int getSeasons_fk() {
        return seasons_fk;
    }

    public void setSeasons_fk(int seasons_fk) {
        this.seasons_fk = seasons_fk;
    }
}
