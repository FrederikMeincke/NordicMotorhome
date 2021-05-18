package com.nordicmotorhome.Model;

public class Location {
    private int id;
    private String address;
    private double distance;

    public Location() {
    }

    public Location(int id, String address, double distance) {
        this.id = id;
        this.address = address;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
