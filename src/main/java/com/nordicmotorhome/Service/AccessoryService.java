package com.nordicmotorhome.Service;

import com.nordicmotorhome.Model.Accessory;
import com.nordicmotorhome.Repository.AccessoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessoryService {
    @Autowired
    AccessoryRepo accessoryRepo;

    public List<Accessory> fetchAll() {
        return accessoryRepo.fetchAll();
    }
}
