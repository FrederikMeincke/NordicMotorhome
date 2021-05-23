package com.nordicmotorhome.Model;

import java.time.LocalDate;
import java.util.List;

public class Motorhome {

    private enum fuel_type {
        PETROL,
        DIESEL,
        EV
    }
    private enum type {
        Class_A,
        Class_B,
        Class_C,
        Fifth_Wheel_Camper,
        Toy_Hauler,
        Travel_Trailer,
        Teardrop_Camper,
        Pop_Up_Camper
    }
    private int id;
    private String brand;
    private String model;
    private double price;
    private double weight;
    private double width;
    private double height;
    private LocalDate register_date;
    private String license_plate;
    private int bed_amount;
    private double odometer;
    private List<Utility> utilityList;
    private int ready_status_tmp;
    private boolean ready_status;
    private String service_raport;

    private String fuel_type;

    private String type;


    public Motorhome() {
    }

    public Motorhome(int id, String brand, String model, double price, double width, double height, LocalDate register_date,
                     String license_plate, int bed_amount, double odometer, List<Utility> utilityList, int ready_status_tmp, String service_report) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.width = width;
        this.height = height;
        this.register_date = register_date;
        this.license_plate = license_plate;
        this.bed_amount = bed_amount;
        this.odometer = odometer;
        this.utilityList = utilityList;
        this.ready_status_tmp = ready_status_tmp;
        this.service_raport = service_report;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public LocalDate getRegister_date() {
        return register_date;
    }

    public void setRegister_date(LocalDate register_date) {
        this.register_date = register_date;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public int getBed_amount() {
        return bed_amount;
    }

    public void setBed_amount(int bed_amount) {
        this.bed_amount = bed_amount;
    }

    public double getOdometer() {
        return odometer;
    }

    public void setOdometer(double odometer) {
        this.odometer = odometer;
    }

    public List<Utility> getUtilityList() {
        return utilityList;
    }

    public void setUtilityList(List<Utility> utilityList) {
        this.utilityList = utilityList;
    }

    public int getReady_status_tmp() {
        return ready_status_tmp;
    }

    public void setReady_status_tmp(int ready_status_tmp) {
        this.ready_status_tmp = ready_status_tmp;
        if (ready_status_tmp == 0) {
            ready_status = false;
        }
        if (ready_status_tmp == 1) {
            ready_status = true;
        }
    }

    public boolean getReady_status() {
        return ready_status;
    }

    public void setReady_status(boolean ready_status) {
        this.ready_status = ready_status;
    }

    public String getService_raport() {
        return service_raport;
    }

    public void setService_raport(String service_raport) {
        this.service_raport = service_raport;
    }

    public String getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(String fuel_type) {
        this.fuel_type = fuel_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {


        this.type = type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
