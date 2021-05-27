package com.nordicmotorhome.Service;

import com.nordicmotorhome.Model.Rental;
import com.nordicmotorhome.Repository.RentalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {

    @Autowired
    RentalRepo rentalRepo;

    public List<Rental> fetchAll() {
        return rentalRepo.fetchAll();
    }

    public Rental findById(int id) {
        return rentalRepo.findById(id);
    }

    public void addNew(Rental rental) {
        rentalRepo.addNew(rental);
    }

    public void update(int id, Rental rental) {
        rentalRepo.update(id, rental);
    }

    public String delete(int id) {
       rentalRepo.delete(id);
        return "/home/showAllRentals";
    }
}
