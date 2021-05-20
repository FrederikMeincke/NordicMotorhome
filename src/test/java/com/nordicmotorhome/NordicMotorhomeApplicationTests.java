package com.nordicmotorhome;

import com.nordicmotorhome.Repository.DatabaseRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class NordicMotorhomeApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    DatabaseRepo databaseRepo;

    @Test
    void contextLoads() {

    }

}
