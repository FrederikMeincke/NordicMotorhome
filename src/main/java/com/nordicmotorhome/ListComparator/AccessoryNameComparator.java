package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Accessory;

import java.util.Comparator;

public class AccessoryNameComparator implements Comparator<Accessory> {
    public int compare(Accessory a1, Accessory a2) {
        return a1.getName().compareTo(a2.getName());
    }
}
