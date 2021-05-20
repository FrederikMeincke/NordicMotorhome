package com.nordicmotorhome.Model;

import java.util.Date;

public class Season {
    private int id;
    private String name;
    private double modifier;
    private Date start_date;
    private Date end_date;

    public Season() {
    }

    public Season(int id, String name, double modifier, Date start_date, Date end_date) {
        this.id = id;
        this.name = name;
        this.modifier = modifier;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getModifier() {
        return modifier;
    }

    public void setModifier(double modifier) {
        this.modifier = modifier;
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
}
