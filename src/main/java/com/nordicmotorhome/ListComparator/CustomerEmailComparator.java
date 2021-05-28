package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Customer;

import java.util.Comparator;

public class CustomerEmailComparator implements Comparator<Customer> {
    public int compare(Customer c1, Customer c2) {
        return c1.getEmail().compareTo(c2.getEmail());
    }
}
