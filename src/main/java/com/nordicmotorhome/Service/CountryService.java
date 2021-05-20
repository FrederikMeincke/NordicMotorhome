package com.nordicmotorhome.Service;

import com.nordicmotorhome.Model.Country;
import com.nordicmotorhome.Repository.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    CountryRepo countryRepo;

    public List<Country> fetchAllCountries() {
        return countryRepo.fetchAllCountries();
    }
}
