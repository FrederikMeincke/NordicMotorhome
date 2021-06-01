package com.nordicmotorhome.Service;

import com.nordicmotorhome.ListComparator.*;
import com.nordicmotorhome.Model.Customer;
import com.nordicmotorhome.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    public List<Customer> fetchAll() {
        return customerRepo.fetchAll();
    }

    /**
     * @author Kasper
     * The method checks whether any of the mandatory input fields are left empty and if so, directs the user to a
     * webpage that explains what went wrong. If all mandatory input fields are filled out, a customer entry is added
     * to the database.
     * @param customer
     * @return
     */
    public String addCustomer(Customer customer){
        boolean emptyField =
                customer.getFirst_name().isEmpty() || customer.getLast_name().isEmpty() ||
                        customer.getMobile().isEmpty() || customer.getEmail().isEmpty() ||
                        customer.getDrivers_license().isEmpty() || customer.getDl_issue_date().isEmpty() ||
                        customer.getDl_expire_date().isEmpty() || customer.getStreet().isEmpty() ||
                        customer.getZip().isEmpty() || customer.getCity().isEmpty() ||
                        customer.getCountry().isEmpty();
        if(emptyField) {
            return "home/error/errorPage";
        } else {
            customerRepo.addNew(customer);
            return "redirect:/showAllCustomers";
        }
    }

    /**
     * @author Kasper
     * The method checks whether any of the mandatory input fields are left empty and if so, directs the user to a
     * webpage that explains what went wrong. If all mandatory input fields are filled out, the update method in
     * customerRepo is called and the user is redirected to the previous page.
     * @param customer
     * @return
     */
    public String updateCustomer(Customer customer, int id) {
        boolean emptyField =
                customer.getFirst_name().isEmpty() || customer.getLast_name().isEmpty() ||
                        customer.getMobile().isEmpty() || customer.getEmail().isEmpty() ||
                        customer.getDrivers_license().isEmpty() || customer.getDl_issue_date().isEmpty() ||
                        customer.getDl_expire_date().isEmpty() || customer.getStreet().isEmpty() ||
                        customer.getZip().isEmpty() || customer.getCity().isEmpty() ||
                        customer.getCountry().isEmpty();
        if(emptyField) {
            return "home/error/errorPage";
        } else {
            customerRepo.update(customer, id);
            return "redirect:/showAllCustomers";
        }
    }

    public Customer findCustomerByID(int id) {
        return customerRepo.findById(id);
    }

    /**
     * @author Jimmy
     * Uses a boolean to determine what address String to return.
     * @param id
     * @return
     */
    public String deleteCustomer(int id) {
        if(customerRepo.hasConstraint(id)) {
            return "/home/error/errorCustomer";
        } else {
            customerRepo.delete(id);
            return "redirect:/showAllCustomers";
        }
    }

    /**
     * @author Jimmy
     * Sorts the displayed accessory list by the identifying String sort.
     * @param list List
     * @param sort String
     */
    public void sort(List<Customer> list, String sort) {
        switch (sort) {
            case "name":
                Collections.sort(list, new CustomerNameComparator());
                break;
            case "mobile":
                Collections.sort(list, new CustomerMobileComparator());
                break;
            case "phone":
                Collections.sort(list, new CustomerPhoneComparator());
                break;
            case "email":
                Collections.sort(list, new CustomerEmailComparator());
                break;
            case "drivers-license":
                Collections.sort(list, new CustomerDLComparator());
                break;
            default:
                break;
        }
    }
}
