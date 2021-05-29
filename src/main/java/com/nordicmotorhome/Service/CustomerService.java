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

    public String deleteCustomer(int id) {
        if(customerRepo.hasConstraint(id)) {
            return "/home/error/errorCustomer";
        } else {
            customerRepo.delete(id);
            return "redirect:/showAllCustomers";
        }
    }

    public boolean hasConstraint(int id) {
        return customerRepo.hasConstraint(id);
    }

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
