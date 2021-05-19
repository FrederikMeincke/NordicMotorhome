package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerRepo {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Customer> fetchAllCustomers() {
        String sqluse = "USE NMR;";
        String sqlCustomer =
                "SELECT customers.id, first_name, last_name, mobile, phone, email, drivers_license, dl_issue_date, " +
                "dl_expire_date, street, floor, zip, city, name as 'country' FROM NMR.customers " +
                "INNER JOIN addresses on addresses.id = addresses_fk " +
                "INNER JOIN zip_codes on zip_codes.id = zip_codes_fk " +
                "INNER JOIN countries on countries.id = countries_fk;";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        jdbcTemplate.update(sqluse);
        return jdbcTemplate.query(sqlCustomer,rowMapper);
    }

    /**
     * @author Jimmy Losang
     * A customer has an address field which needs a valid zip code, so we must first either insert a new zip code into
     * the database if one doesn't already exist, or use an existing zip code from the database. This zip code also needs
     * a valid country, so we must first check for countries.
     * @param customer
     */
   /*
    public void addCustomer(Customer customer) {
        String sqlCountry = "IF NOT EXISTS (SELECT * FROM countries " +
                "WHERE name = ?) " +
                "INSERT INTO countries " +
                "VALUES (DEFAULT, ?);";
        jdbcTemplate.update(sqlCountry, customer.getCountry(), customer.getCountry());
    }
*/

    public void addCustomer(Customer customer) {
        String sqlZipCity = "INSERT INTO zip_codes (id, zip, city, countries_fk) VALUES (DEFAULT, ?, ?, '58')";

        jdbcTemplate.update(sqlZipCity, customer.getZip(), customer.getCity());
        String sqlLastAddedZip = "SELECT id FROM NMR.zip_codes ORDER BY id DESC limit 1;";
        SqlRowSet zip = jdbcTemplate.queryForRowSet(sqlLastAddedZip);
        zip.next();
        int zipInt = zip.getInt("id");

        String sqlAddress = "INSERT INTO addresses (id, street, floor, zip_codes_fk) VALUES (DEFAULT,?,?," + zipInt + ");";

        jdbcTemplate.update(sqlAddress,customer.getStreet(),customer.getFloor());
        String sqlLastAddedAddress = "SELECT id FROM NMR.addresses ORDER BY id DESC limit 1;";
        SqlRowSet adr = jdbcTemplate.queryForRowSet(sqlLastAddedAddress);
        adr.next();
        int addressInt = adr.getInt("id");

        String sqlCustomer = "INSERT INTO customers (id ,first_name, last_name , mobile, phone, email, " +
                "drivers_license, dl_issue_date, dl_expire_date, addresses_fk) VALUES (DEFAULT,?,?,?,?,?,?,?,?," + addressInt + ");";

        jdbcTemplate.update(sqlCustomer,customer.getFirst_name(),customer.getLast_name(),customer.getMobile(),
                customer.getPhone(),customer.getEmail(),customer.getDrivers_license(),
                customer.getDl_issue_date(),customer.getDl_expire_date());
    }



}
