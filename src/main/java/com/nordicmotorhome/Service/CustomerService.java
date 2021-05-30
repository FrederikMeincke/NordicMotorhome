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

    public void addCustomer(Customer customer){
        customerRepo.addNew(customer);
    }

    public void updateCustomer(Customer customer, int id) {
        customerRepo.update(customer, id);
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

    /**
     *
     * @param list
     * @param sort
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
