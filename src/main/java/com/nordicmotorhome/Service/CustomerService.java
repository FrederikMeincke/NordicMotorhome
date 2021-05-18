package com.nordicmotorhome.Service;

import com.nordicmotorhome.Model.Customer;
import com.nordicmotorhome.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    public List<Customer> fetchAllCustomers() {
        return customerRepo.fetchAllCustomers();
    }

}
