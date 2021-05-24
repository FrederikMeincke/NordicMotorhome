package com.nordicmotorhome.Service;

import com.nordicmotorhome.Model.Customer;
import com.nordicmotorhome.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    public List<Customer> fetchAllCustomers() {
        return customerRepo.fetchAllCustomers();
    }

    public void addCustomer(Customer customer){
        customerRepo.addCustomer(customer);
    }

    public void updateCustomer(Customer customer, int id) {
        customerRepo.updateCustomer(customer, id);
    }

    public Customer findCustomerByID(int id) {
        return customerRepo.findCustomerByID(id);
    }

    public String deleteCustomer(int id) {
        if(customerRepo.hasConstraint(id)) {
            return "/home/error/errorCustomer";
        } else {
            customerRepo.deleteCustomer(id);
            return "redirect:/showAllCustomers";
        }
    }

    public boolean hasConstraint(int id) {
        return customerRepo.hasConstraint(id);
    }

}
