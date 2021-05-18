package com.nordicmotorhome.Model;

public class Season {
    private int id;
    private String name;
    private double modifier;

    public Season() {
    }

    public Season(int id, String name, double modifier) {
        this.id = id;
        this.name = name;
        this.modifier = modifier;
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
}
