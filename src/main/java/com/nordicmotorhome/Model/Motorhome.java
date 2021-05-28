package com.nordicmotorhome.Model;

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
    private int models_fk;
    private double price;
    private double weight;
    private double width;
    private double height;
    private String register_date;
    private String license_plate;
    private int bed_amount;
    private String distance_driven;
    private boolean[] utilityArray = new boolean[8];
    private boolean ready_status;
    private String service_raport;
    private String fuel_type;
    private String type;

    private int utility_0;
    private int utility_1;
    private int utility_2;
    private int utility_3;
    private int utility_4;
    private int utility_5;
    private int utility_6;
    private int utility_7;

    public Motorhome() {
        for(boolean bool : utilityArray) {
            bool = false;
        }
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public boolean isReady_status() {
        return ready_status;
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

    public String getDistance_driven() {
        return distance_driven;
    }

    public void setDistance_driven(String distance_driven) {
        this.distance_driven = distance_driven;
    }

    public boolean[] getUtilityArray() {
        return utilityArray;
    }

    public void setUtilityArray(boolean[] utilityArray) {
        this.utilityArray = utilityArray;
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

    public int getModels_fk() {
        return models_fk;
    }

    public void setModels_fk(int models_fk) {
        this.models_fk = models_fk;
    }

    public int getUtility_0() {
        return utility_0;
    }

    public void setUtility_0(int utility_0) {
        this.utility_0 = utility_0;
    }

    public int getUtility_1() {
        return utility_1;
    }

    public void setUtility_1(int utility_1) {
        this.utility_1 = utility_1;
    }

    public int getUtility_2() {
        return utility_2;
    }

    public void setUtility_2(int utility_2) {
        this.utility_2 = utility_2;
    }

    public int getUtility_3() {
        return utility_3;
    }

    public void setUtility_3(int utility_3) {
        this.utility_3 = utility_3;
    }

    public int getUtility_4() {
        return utility_4;
    }

    public void setUtility_4(int utility_4) {
        this.utility_4 = utility_4;
    }

    public int getUtility_5() {
        return utility_5;
    }

    public void setUtility_5(int utility_5) {
        this.utility_5 = utility_5;
    }

    public int getUtility_6() {
        return utility_6;
    }

    public void setUtility_6(int utility_6) {
        this.utility_6 = utility_6;
    }

    public int getUtility_7() {
        return utility_7;
    }

    public void setUtility_7(int utility_7) {
        this.utility_7 = utility_7;
    }
}
