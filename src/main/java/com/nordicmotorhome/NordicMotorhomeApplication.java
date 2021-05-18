package com.nordicmotorhome;

import com.nordicmotorhome.Model.Customer;
import com.nordicmotorhome.Repository.CustomerRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class NordicMotorhomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NordicMotorhomeApplication.class, args);
        //new CustomerRepo().initializeDatabase();
    }

}
