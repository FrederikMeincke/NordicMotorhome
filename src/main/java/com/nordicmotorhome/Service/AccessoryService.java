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

    /**
     * @author Jimmy
     * Due to design choices we aren't using this method anywhere, but we have decided to leave it in in case we
     * want to be able to add more accessories through the program. The method addNew in accessoryRepo works just fine.
     * @param accessory
     */
    public void addNew(Accessory accessory) {
        accessoryRepo.addNew(accessory);
    }

    /**
     * Has no validation because it is allowed to be empty by design, in case the owners want to remove the option of
     * an accessory.
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

    /**
     * @author Jimmy
     * Uses a boolean to determine what address String to return.
     * @param id
     * @return String
     */
    public String delete(int id) {
        if(accessoryRepo.hasConstraint(id)) {
            return "/home/error/errorAccessory";
        } else {
            accessoryRepo.delete(id);
            return "redirect:/showAllAccessories";
        }
    }

    /**
     * @Author Jimmy
     * Sorts the displayed accessory list by the identifying String sort.
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
