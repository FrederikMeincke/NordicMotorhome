package com.nordicmotorhome.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository

public class MotorhomeRepo {
    @Autowired
    JdbcTemplate jdbcTemplate;
}
