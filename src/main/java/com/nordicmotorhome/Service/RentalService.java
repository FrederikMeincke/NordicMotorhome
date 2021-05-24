package com.nordicmotorhome.Service;

import com.nordicmotorhome.Repository.RentalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    @Autowired
    RentalRepo rentalRepo;
}
