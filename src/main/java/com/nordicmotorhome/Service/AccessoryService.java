package com.nordicmotorhome.Service;

import com.nordicmotorhome.ListComparator.AccessoryNameComparator;
import com.nordicmotorhome.ListComparator.AccessoryPriceComparator;
import com.nordicmotorhome.Model.Accessory;
import com.nordicmotorhome.Repository.AccessoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
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

    /**
     * Has no validation because it is allowed to be empty by design, in case the owners want to remove the option of an accessory.
     * @Author Jimmy
     * @param id
     * @param accessory
     */
    public void update(int id, Accessory accessory) {
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

    /**
     * @Author Jimmy
     * @param list
     * @param sort
     */
    public void sort(List<Accessory> list, String sort) {
        switch (sort) {
            case "name":
                Collections.sort(list, new AccessoryNameComparator());
                break;
            case "price":
                Collections.sort(list, new AccessoryPriceComparator());
            default:
                break;
        }
    }
}
