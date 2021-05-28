package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Accessory;
import com.nordicmotorhome.Model.Customer;

import java.util.Comparator;

public class CustomerMobileComparator implements Comparator<Customer> {
    public int compare(Customer c1, Customer c2) {
        return c1.getMobile().compareTo(c2.getMobile());
    }

}
