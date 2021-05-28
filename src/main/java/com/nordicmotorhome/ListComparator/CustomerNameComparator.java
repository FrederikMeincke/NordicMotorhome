package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Accessory;
import com.nordicmotorhome.Model.Customer;

import java.util.Comparator;

public class CustomerNameComparator implements Comparator<Customer> {
    public int compare(Customer c1, Customer c2) {
        return c1.getFirst_name().compareTo(c2.getFirst_name());
    }
}
