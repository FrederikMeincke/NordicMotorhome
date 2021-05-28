package com.nordicmotorhome.Model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Calculator {

    public static double rentalPrice(Rental rental) {
        LocalDate start_date = LocalDate.parse(rental.getStart_date());
        LocalDate end_date = LocalDate.parse(rental.getEnd_date());
        List<Accessory> accessoryList = rental.getAccessoryList();
        int days = (int) ChronoUnit.DAYS.between(start_date, end_date);

        double accessoriesPrice = 0;
        if(accessoryList != null) {
            for(Accessory accessory : accessoryList) {
                accessoriesPrice += accessory.getPrice();
            }
        }

        int fuelPrice = rental.isHalfFuel() ? 70 : 0;
        double distancePrice = (rental.getPick_up_distance()+rental.getDrop_off_distance()) * 0.7; // 0.7€ per kilometer
        double averageDistance = rental.getDistance_driven() / days;
        int averageDistancePrice = 0;
        if(averageDistance > 400) {
            averageDistancePrice = (int) averageDistance-400; // removes any decimals after "."
        }
        double motorhomePrice = rental.getMotorhome().getPrice();
        double seasonRate = rental.getSeason().getRate();
        double totalPrice = (days * motorhomePrice * seasonRate + accessoriesPrice + averageDistancePrice +
                distancePrice) * cancelFee(rental);

        if(totalPrice < 200) {
            return 200;
        } else {
            return totalPrice;
        }
    }

    private static double cancelFee(Rental rental) {
        int days = -1;
        try {
            LocalDate cancel_date = LocalDate.parse(rental.getCancel_date());
            LocalDate start_date = LocalDate.parse(rental.getStart_date());
            days = (int) ChronoUnit.DAYS.between(cancel_date, start_date);
            System.out.println(cancel_date);
            System.out.println(start_date);
            System.out.println(days);
        } catch (NullPointerException | DateTimeParseException exception) {
            return 1;
        }

        if(days <= 0) {
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
