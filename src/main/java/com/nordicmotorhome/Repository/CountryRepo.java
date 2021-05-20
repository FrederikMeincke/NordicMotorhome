package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;


    /**
     * @Author Kasper N. Jensen
     * @return jdbcTemplate
     */
    public List<Country> fetchAllCountries(){
        String sqlUse = "USE NMR;";
        String sqlZip = "SELECT * FROM NMR.countries;";
        RowMapper<Country> rowMapper = new BeanPropertyRowMapper<>(Country.class);
        jdbcTemplate.update(sqlUse);
        return jdbcTemplate.query(sqlZip,rowMapper);
    }
}
