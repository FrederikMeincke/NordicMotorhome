package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Customer;

import java.util.Comparator;

public class CustomerPhoneComparator implements Comparator<Customer> {

    /**
     *
     * @param c1
     * @param c2
     * @return
     */
    public int compare(Customer c1, Customer c2) {
        int compare = 0;
        try {
            compare = c1.getPhone().compareTo(c2.getPhone());
        } catch (NullPointerException exception) {
            if (c1.getPhone() == null) {
                compare = 1;
            } else if (c2.getPhone() == null) {
                compare = -1;
            }
        }
        return compare;
    }
}