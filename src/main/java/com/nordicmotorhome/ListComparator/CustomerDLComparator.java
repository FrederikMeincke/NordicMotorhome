package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Customer;

import java.util.Comparator;

public class CustomerDLComparator implements Comparator<Customer> {

    /**
     * @author Jimmy
     * the String class already contains a compareTo method that we can use. This Comparator is still necessary as it
     * specifies the String in the Customer class that needs to be compared.
     * @param c1
     * @param c2
     * @return
     */
    public int compare(Customer c1, Customer c2) {
        return c1.getDrivers_license().compareTo(c2.getDrivers_license());
    }
}
