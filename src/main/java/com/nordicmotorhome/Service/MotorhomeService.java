package com.nordicmotorhome.Service;

import com.nordicmotorhome.Model.Motorhome;
import com.nordicmotorhome.Repository.MotorhomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorhomeService {

    @Autowired
    MotorhomeService motorhomeRepo;

    public List<Motorhome> fetchAllMotorhomes() {
        return motorhomeRepo.fetchAllMotorhomes();
    }

}


