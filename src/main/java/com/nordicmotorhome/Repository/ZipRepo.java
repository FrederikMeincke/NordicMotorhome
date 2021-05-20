package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Zip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ZipRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Zip> fetchAllZips(){
        String sqlUse = "USE NMR;";
        String sqlZip = "SELECT * FROM NMR.zip_codes;";
        RowMapper<Zip> rowMapper = new BeanPropertyRowMapper<>(Zip.class);
        jdbcTemplate.update(sqlUse);
        return jdbcTemplate.query(sqlZip,rowMapper);
    }

}
