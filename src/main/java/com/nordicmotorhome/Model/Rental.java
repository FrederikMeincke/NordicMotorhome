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
    private Location pick_up_location;
    private Location drop_off_location;
    private double total_price;

    public Rental() {
    }

    public Rental(int id, Customer customer, Motorhome motorhome, Season season, List<Accessory> accessoryList,
                  Date start_date, Date end_date, Location pick_up_location, Location drop_off_location,
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

    public Location getPick_up_location() {
        return pick_up_location;
    }

    public void setPick_up_location(Location pick_up_location) {
        this.pick_up_location = pick_up_location;
    }

    public Location getDrop_off_location() {
        return drop_off_location;
    }

    public void setDrop_off_location(Location drop_off_location) {
        this.drop_off_location = drop_off_location;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }
}
