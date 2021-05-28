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

    public void addMotorhome(Motorhome motorhome){
        motorhomeRepo.addNew(motorhome);
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


