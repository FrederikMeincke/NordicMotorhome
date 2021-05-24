package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Accessory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccessoryRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Accessory> fetchAll() {
        String sqlUse = "USE NMR;";
        jdbcTemplate.update(sqlUse);
        String sql = "SELECT * FROM nmr.accessories;";
        RowMapper rowMapper = new BeanPropertyRowMapper<>(Accessory.class);
        return jdbcTemplate.query(sql, rowMapper);
    }
}
