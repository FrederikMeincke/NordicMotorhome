package com.nordicmotorhome.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Rental {

    private int id;
    private Customer customer;
    private Motorhome motorhome;
    private Season season;
    private List<Accessory> accessoryList = new ArrayList<>(); // Used to be a LinkedList because we initially made it possible to add and remove accessories
    private String start_date;
    private String end_date;
    private String pick_up_location;
    private String drop_off_location;
    private int pick_up_distance;
    private int drop_off_distance;
    private int distance_driven;
    private boolean half_fuel;
    private String cancel_date;
    private double total_price;
    private int customers_fk;
    private int motorhomes_fk;
    private int seasons_fk;

    private boolean[] acList = new boolean[16];

    public Rental() {
        for(int i = 0; i < acList.length; i++) {
            acList[i] = false;
        }
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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
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

        LocalDate startSeasonDate = LocalDate.parse(start_date);
        if(startSeasonDate.getMonthValue() >= 11 || startSeasonDate.getMonthValue() <3 ) // TODO: maybe remove >=1
        {
            setSeasons_fk(1);
        } else if(startSeasonDate.getMonthValue() >= 3 && startSeasonDate.getMonthValue() < 5) {
            setSeasons_fk(2);
        } else if(startSeasonDate.getMonthValue() >=5 && startSeasonDate.getMonthValue() < 9) {
            setSeasons_fk(3);
        } else {
            setSeasons_fk(2);
        }
        return seasons_fk;
    }

    public void setSeasons_fk(int seasons_fk) {
        this.seasons_fk = seasons_fk;
    }

    public int getPick_up_distance() {
        return pick_up_distance;
    }

    public void setPick_up_distance(int pick_up_distance) {
        this.pick_up_distance = pick_up_distance;
    }

    public int getDrop_off_distance() {
        return drop_off_distance;
    }

    public void setDrop_off_distance(int drop_off_distance) {
        this.drop_off_distance = drop_off_distance;
    }

    public String getCancel_date() {
        return cancel_date;
    }

    public void setCancel_date(String cancel_date) {
        this.cancel_date = cancel_date;
    }

    public boolean[] getAcList() {
        return acList;
    }

    public void setAcList(boolean[] acList) {
        this.acList = acList;
    }

    public String getAccessories() {
        String accessories = "";
        try {
            accessories += accessoryList.get(0).getName();
            for(int i = 1; i < accessoryList.size(); i++) {
                accessories += ", " + accessoryList.get(i).getName();
            }
        } catch (IndexOutOfBoundsException exception) {

        }
        return accessories;
    }

    public int getDistance_driven() {
        return distance_driven;
    }

    public void setDistance_driven(int distance_driven) {
        this.distance_driven = distance_driven;
    }

    public boolean isHalf_fuel() {
        return half_fuel;
    }

    public void setHalf_fuel(boolean half_fuel) {
        this.half_fuel = half_fuel;
    }
}