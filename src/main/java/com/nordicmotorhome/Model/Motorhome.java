package com.nordicmotorhome.Model;

import java.util.Date;
import java.util.List;

public class Motorhome {

    private enum fuel_type {
        PETROL,
        DIESEL,
        ELECTRIC
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
    private double width;
    private double height;
    private Date register_date;
    private String license_plate;
    private int bed_Amount;
    private double odometer;
    private List<Utility> utilityList;
    private int ready_status;
    private String service_raport;

    private fuel_type fuel_type;

    private type type;


    public Motorhome() {
    }

    public Motorhome(int id, String brand, String model, double price, double width, double height, Date register_date,
                     String license_plate, int bed_Amount, double odometer, List<Utility> utilityList, int ready_status, String service_report) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.width = width;
        this.height = height;
        this.register_date = register_date;
        this.license_plate = license_plate;
        this.bed_Amount = bed_Amount;
        this.odometer = odometer;
        this.utilityList = utilityList;
        this.ready_status = ready_status;
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

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public int getBed_Amount() {
        return bed_Amount;
    }

    public void setBed_Amount(int bed_Amount) {
        this.bed_Amount = bed_Amount;
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

    public int getReady_status() {
        return ready_status;
    }

    public void setReady_status(int ready_status) {
        this.ready_status = ready_status;
    }

    public String getService_raport() {
        return service_raport;
    }

    public void setService_raport(String service_raport) {
        this.service_raport = service_raport;
    }

    public Motorhome.fuel_type getFuel_type() {
        return fuel_type;
    }

    public void setFuel_type(Motorhome.fuel_type fuel_type) {
        this.fuel_type = fuel_type;
    }

    public Motorhome.type getType() {
        return type;
    }

    public void setType(Motorhome.type type) {
        this.type = type;
    }
}
