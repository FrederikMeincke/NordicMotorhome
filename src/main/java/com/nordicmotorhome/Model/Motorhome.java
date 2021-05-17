package com.nordicmotorhome.Model;

import java.util.Date;
import java.util.List;

public class Motorhome {

    private enum fuelType {
        BENZIN,
        DIEZEL,
        BATTERY
    }
    private int id;
    private String type;
    private String brand;
    private String model;
    private double price;
    private double width;
    private double height;
    private Date register_Date;
    private String license_plate;
    private int bed_Amount;
    private double odometer;
    private List<Utility> utilityList;
    private String service_report;

    public Motorhome() {
    }


}
