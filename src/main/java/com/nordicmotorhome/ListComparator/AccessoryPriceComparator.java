package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Accessory;

import java.util.Comparator;

public class AccessoryPriceComparator implements Comparator<Accessory> {

    public int compare(Accessory a1, Accessory a2) {
        int compare = 0;
        if(a1.getPrice() > a2.getPrice()) {
            compare = 1;
        } else if(a1.getPrice() < a2.getPrice()) {
            compare = -1;
        } else {
            compare = 0;
        }
        return compare;
    }
}
