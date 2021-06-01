package com.nordicmotorhome.ListComparator;

import com.nordicmotorhome.Model.Customer;

import java.util.Comparator;

public class CustomerPhoneComparator implements Comparator<Customer> {

    /**
     * @author Jimmy
     * the String class already contains a compareTo method that we can use. This Comparator is still necessary as it
     * specifies the String in the Accessory class that needs to be compared. However the field is also allowed to be
     * empty by design, and as such we need to be able to compare null objects.
     * @param c1
     * @param c2
     * @return int
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