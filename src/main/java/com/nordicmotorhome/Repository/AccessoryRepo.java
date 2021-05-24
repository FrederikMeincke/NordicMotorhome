package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Accessory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccessoryRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

}
