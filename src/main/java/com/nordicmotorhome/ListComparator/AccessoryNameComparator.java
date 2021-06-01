package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Accessory;

import java.util.Comparator;

public class AccessoryNameComparator implements Comparator<Accessory> {

    /**
     * @author Jimmy
     * the String class already contains a compareTo method that we can use. This Comparator is still necessary as it
     * specifies the String in the Accessory class that needs to be compared.
     * @param a1
     * @param a2
     * @return int
     */
    public int compare(Accessory a1, Accessory a2) {
        return a1.getName().compareTo(a2.getName());
    }
}
