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
        return motorhomeRepo.fetchAllMotorhomes();
    }

    public void addMotorhome(Motorhome motorhome){
        motorhomeRepo.addMotorhome(motorhome);
    }

    public String deleteMotorhome(int id) {
        if(motorhomeRepo.hasConstraint(id)) {
            return "/home/error/errorMotorhome";
        } else  {
            motorhomeRepo.deleteMotorhome(id);
            return "redirect:/showAllMotorhomes";
        }
    }

    public void updateMotorhome(int id) {
        //motorhomeRepo.update(id);
    }

    public Motorhome findMotorhomeById(int id) {
        return motorhomeRepo.findById(id);
    }

}


