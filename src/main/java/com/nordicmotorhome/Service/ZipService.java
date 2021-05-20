package com.nordicmotorhome.Service;

import com.nordicmotorhome.Model.Zip;
import com.nordicmotorhome.Repository.ZipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZipService {

    @Autowired
    ZipRepo zipRepo;

    public List<Zip> fetchAllZips() {
        return zipRepo.fetchAllZips();
    }
}
