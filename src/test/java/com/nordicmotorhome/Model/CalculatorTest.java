package com.nordicmotorhome.Model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    static Rental rental;

    @BeforeEach
    void setup() {
        rental = new Rental();
        Motorhome motorhome = new Motorhome();
        motorhome.setPrice(1000);

        rental.setMotorhome(motorhome);

        Season season = new Season();
        season.setRate(2);

        rental.setSeason(season);

        Accessory accessory1 = new Accessory();
        Accessory accessory2 = new Accessory();
        accessory1.setPrice(1);
        accessory2.setPrice(1);

        rental.setAccessoryList(List.of(accessory1, accessory2));

        rental.setStart_date("2021-02-01");
        rental.setEnd_date("2021-03-01");        //28 days between

        rental.setPick_up_distance(100);        //70
        rental.setDrop_off_distance(100);       //70
    }

    @Test
    void rentalPrice() {
        rental.setDistance_driven(12000);       //800 km more than allowed
        rental.setHalf_fuel(true);              //less than half fuel in tank 70


        double expectedPrice = 57012;
        double actualPrice = Calculator.rentalPrice(rental);

        assertEquals(expectedPrice, actualPrice);
    }

    @Test
    void cancelFee() {
        rental.setCancel_date("2021-02-01");

        assertEquals(11228.4 ,Calculator.rentalPrice(rental));
    }

}