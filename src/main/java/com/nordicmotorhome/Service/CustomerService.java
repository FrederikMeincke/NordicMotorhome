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

    public void deleteCustomer(int id) {
        customerRepo.deleteCustomer(id);
    }

}
