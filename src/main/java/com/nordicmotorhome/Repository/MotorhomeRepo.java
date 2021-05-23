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
        String sqlMotorhome =
                "SELECT motorhomes.id, b.name, m.name, price, m.fuel_type, type, bed_amount, m.weight, m.width, m.height,\n" +
                        "       license_plate, register_date, odometer, ready_status FROM motorhomes\n" +
                        "    INNER JOIN models m on motorhomes.models_fk = m.id\n" +
                        "    INNER JOIN brands b on m.brands_fk = b.id\n" +
                        "ORDER BY motorhomes.id;";
        RowMapper<Motorhome> rowMapper = new BeanPropertyRowMapper<>(Motorhome.class);
        jdbcTemplate.update(sqluse);
        return jdbcTemplate.query(sqlMotorhome,rowMapper);
    }
}
