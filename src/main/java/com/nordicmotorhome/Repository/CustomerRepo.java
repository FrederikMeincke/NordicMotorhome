package com.nordicmotorhome.Repository;

import com.nordicmotorhome.Model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    /**
     * @Author Kasper N. Jensen
     * @param customer Customer
     * This method adds a customer to our MySQL database.
     * Adding a customer to a database over multiple tables with dependencies and forign keys,
     * has to be done in a specific order as you can't add a customer before you have added its address furthermore
     * a zip and city has to be created before the address can be added, this results in 3 SQL statements that has to
     * executed one after another. As all of these steps need to happen you could make these as a transaction
     * in SQL so that if there are any data loss we can rollback in the DB.
     */
    public void addCustomer(Customer customer) {

        //SQL Statement that inserts later declared wildcard variables (?) into the DB
        String sqlZipCity = "INSERT INTO zip_codes (id, zip, city, countries_fk) VALUES (DEFAULT, ?, ?, '58')";

        //Using the jdbcTemplate method 'update', we take the SQL statement, and the needed
        // values from our Customer object using getters
        jdbcTemplate.update(sqlZipCity, customer.getZip(), customer.getCity());

        //Finds the latest added zip entry in the DB and saves the id into a variable
        String sqlLastAddedZip = "SELECT id FROM NMR.zip_codes ORDER BY id DESC limit 1;";

        //queryForRowSet returns a SqlRowSet, much like a ResultSet, we use this to get access to the rows that has
        // the value of sqlLastAddedZip in the id column with zip.next().
        SqlRowSet zip = jdbcTemplate.queryForRowSet(sqlLastAddedZip);
        zip.next();
        int zipInt = zip.getInt("id");

        //The saved zipcode id zipInt is then used as the forign key in the address SQL Query
        String sqlAddress = "INSERT INTO addresses (id, street, floor, zip_codes_fk) VALUES (DEFAULT,?,?," + zipInt + ");";

        //–||–
        jdbcTemplate.update(sqlAddress,customer.getStreet(),customer.getFloor());
        String sqlLastAddedAddress = "SELECT id FROM NMR.addresses ORDER BY id DESC limit 1;";
        SqlRowSet adr = jdbcTemplate.queryForRowSet(sqlLastAddedAddress);
        adr.next();
        int addressInt = adr.getInt("id");

        String sqlCustomer = "INSERT INTO customers (id ,first_name, last_name , mobile, phone, email, " +
                "drivers_license, dl_issue_date, dl_expire_date, addresses_fk) VALUES (DEFAULT,?,?,?,?,?,?,?,?," + addressInt + ");";

        // Lastly everything can be added into the customer using the address forgin key that then uses the zip forgin key
        jdbcTemplate.update(sqlCustomer,customer.getFirst_name(),customer.getLast_name(),customer.getMobile(),
                customer.getPhone(),customer.getEmail(),customer.getDrivers_license(),
                customer.getDl_issue_date(),customer.getDl_expire_date());
    }


    /**
     * Deletes a customer entity from the database with a given primary key
     * @param id the primary key in customer table in the Database
     * @author Mads
     */
    public void deleteCustomer(int id) {
        String sql = "DELETE * FROM customers WHERE id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (DataAccessException e) {
            System.out.println("SQL Error when deleting customer");
        }
    }



}
