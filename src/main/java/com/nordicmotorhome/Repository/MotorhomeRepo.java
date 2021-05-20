package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Customer;
import com.nordicmotorhome.Model.Motorhome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class MotorhomeRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Motorhome> fetchAllMotorhomes(){
        String sqluse = "USE NMR;";


        //needs edit
        String sqlMotorhome =
                "SELECT motorhomes.id, first_name, last_name, mobile, phone, email, drivers_license, dl_issue_date, " +
                        "dl_expire_date, street, floor, zip, city, name as 'country' FROM NMR.customers " +
                        "INNER JOIN utilities on addresses.id = addresses_fk " +
                        "INNER JOIN motorhome_utilities on addresses.id = addresses_fk " +
                        "INNER JOIN models on zip_codes.id = zip_codes_fk " +
                        "INNER JOIN brands on countries.id = countries_fk;";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        jdbcTemplate.update(sqluse);
        return jdbcTemplate.query(sqlMotorhome,rowMapper);
    }
}
