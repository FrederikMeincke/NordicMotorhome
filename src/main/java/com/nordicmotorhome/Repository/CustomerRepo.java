package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;
/*
    public List<Customer> fetchAllCustomers() {
        String sql = "SELECT id, first_name, last_name, mobile, phone, email, drivers_license, dl_issue_date, " +
                "dl_expire_date, "
        return null;
    }
 */

    /**
     * @author Jimmy Losang
     * A customer has an address field which needs a valid zip code, so we must first either insert a new zip code into
     * the database if one doesn't already exist, or use an existing zip code from the database. This zip code also needs
     * a valid country, so we must first check for countries.
     * @param customer
     */
    public void addCustomer(Customer customer) {
        String sqlCountry = "IF NOT EXISTS (SELECT * FROM countries " +
                "WHERE name = ?) " +
                "INSERT INTO countries " +
                "VALUES (DEFAULT, ?);";
        jdbcTemplate.update(sqlCountry, customer.getCountry(), customer.getCountry());
    }
}
