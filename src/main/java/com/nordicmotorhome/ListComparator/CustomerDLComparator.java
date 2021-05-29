package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Customer;

import java.util.Comparator;

public class CustomerDLComparator implements Comparator<Customer> {
    public int compare(Customer c1, Customer c2) {
        return c1.getDrivers_license().compareTo(c2.getDrivers_license());
    }
}
