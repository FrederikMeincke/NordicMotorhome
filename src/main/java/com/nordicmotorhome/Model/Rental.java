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

    public Rental() {
    }
}
