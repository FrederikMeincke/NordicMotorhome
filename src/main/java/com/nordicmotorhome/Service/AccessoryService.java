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

    public void addNew(Accessory accessory) {
        accessoryRepo.addNew(accessory);
    }

    public void update(Accessory accessory, int id) {
        accessoryRepo.update(accessory, id);
    }

    public Accessory findById(int id) {
        return accessoryRepo.findById(id);
    }

    public String delete(int id) {
        if(accessoryRepo.hasConstraint(id)) {
            return "/home/error/error420";
        } else {
            accessoryRepo.delete(id);
            return "redirect:/showAllAccessories";
        }
    }
}
