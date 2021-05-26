package com.nordicmotorhome.Model;

import java.time.temporal.ChronoUnit;

public class Calculator {

    public static double rentalPrice(Rental rental) {
        int days = (int) ChronoUnit.DAYS.between(rental.getStart_date(), rental.getEnd_date());
        double accessoriesPrice = 0;
        for(Accessory accessory : rental.getAccessoryList()) {
            accessoriesPrice += accessory.getPrice();
        }
        double distancePrice = (rental.getPick_up_distance()+rental.getDrop_off_distance()) * 0.7;
        double averageDistance; //TODO: add total distance / days, 1€
        double motorhomePrice = rental.getMotorhome().getPrice();
        double seasonRate = rental.getSeason().getRate();
        double totalPrice = (days * motorhomePrice * seasonRate + accessoriesPrice + distancePrice) * cancelFee(rental);
        if(totalPrice < 200) {
            return 200;
        } else {
            return totalPrice;
        }
    }

    private static double cancelFee(Rental rental) {
        int days = -1;
        try {
            days = (int) ChronoUnit.DAYS.between(rental.getCancelDate(), rental.getStart_date());
        } catch (NullPointerException exception) {
            return 1;
        }

        if(days == 0) {
            return 0.95;
        } else if(days < 15) {
            return 0.8;
        } else if(days < 49) {
            return 0.5;
        } else if(days >= 50) {
            return 0.2;
        }
        return 1;
    }
}
