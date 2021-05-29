package com.nordicmotorhome.Service;

import com.nordicmotorhome.Model.Motorhome;
import com.nordicmotorhome.Repository.MotorhomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorhomeService {

    @Autowired
    MotorhomeRepo motorhomeRepo;

    public List<Motorhome> fetchAllMotorhomes() {
        return motorhomeRepo.fetchAll();
    }

    // Doesn't work properly because of empty string conversions to doubles/ints.
    public String addMotorhome(Motorhome motorhome){
        boolean emptyFields = motorhome.getBrand().isEmpty() || motorhome.getModel().isEmpty() ||
                motorhome.getFuel_type().isEmpty() || motorhome.getType().isEmpty() || motorhome.getBed_amount() == 0 ||
                motorhome.getUtilityArray()[0] || motorhome.getWidth() == 0 || motorhome.getHeight() == 0 ||
                motorhome.getWeight() == 0 || motorhome.getLicense_plate().isEmpty() ||
                motorhome.getRegister_date().isEmpty() || motorhome.getPrice() == 0;
        if(emptyFields) {
            return "home/error/errorPage";
        } else {
            motorhomeRepo.addNew(motorhome);
            return "redirect:/showAllMotorhomes";
        }

    }

    public String deleteMotorhome(int id) {
        if(motorhomeRepo.hasConstraint(id)) {
            return "/home/error/errorMotorhome";
        } else  {
            motorhomeRepo.delete(id);
            return "redirect:/showAllMotorhomes";
        }
    }

    public void updateMotorhome(Motorhome inputMotorhome ,int id) {
        motorhomeRepo.update(inputMotorhome ,id);
    }

    public Motorhome findMotorhomeById(int id) {
        return motorhomeRepo.findById(id);
    }

}


